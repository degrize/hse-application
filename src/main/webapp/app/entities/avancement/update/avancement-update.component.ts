import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AvancementFormService, AvancementFormGroup } from './avancement-form.service';
import { IAvancement } from '../avancement.model';
import { AvancementService } from '../service/avancement.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';
import { IUser } from '../../user/user.model';
import { AccountService } from '../../../core/auth/account.service';
import { UserManagementService } from '../../../admin/user-management/service/user-management.service';
import { User } from '../../../admin/user-management/user-management.model';
import { SessionStorageService } from 'ngx-webstorage';
import Swal from 'sweetalert2';

@Component({
  selector: 'jhi-avancement-update',
  templateUrl: './avancement-update.component.html',
  styleUrls: ['./avancement-update.component.scss'],
})
export class AvancementUpdateComponent implements OnInit {
  isSaving = false;
  avancement: IAvancement | null = null;

  projetsSharedCollection: IProjet[] = [];

  editForm: AvancementFormGroup = this.avancementFormService.createAvancementFormGroup();

  user: IUser = { id: 1, login: '' };
  projetSelected = false;

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected avancementService: AvancementService,
    protected avancementFormService: AvancementFormService,
    protected projetService: ProjetService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService,
    private userManagementService: UserManagementService,
    private sessionStorageService: SessionStorageService
  ) {}

  compareProjet = (o1: IProjet | null, o2: IProjet | null): boolean => this.projetService.compareProjet(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ avancement }) => {
      this.avancement = avancement;
      if (avancement) {
        this.updateForm(avancement);
      }

      this.loadRelationshipsOptions();
    });

    this.accountService.identity().subscribe(account => {
      if (account) {
        const login = account.login;
        if (login) {
          console.log(this.userManagementService.find(login));
          this.userManagementService.find(login).subscribe({
            next: (res: User) => {
              if (res.id) {
                this.user.login = res.login;
                this.user.id = res.id;
              }
            },
            error: () => 'ERREUR',
          });
        }
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('hseApplicationApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  loadProjectSelected(): void {
    if (this.sessionStorageService.retrieve('projetId')) {
      for (let i = 0; i < this.projetsSharedCollection.length; i++) {
        if (this.sessionStorageService.retrieve('projetId') == this.projetsSharedCollection[i].id) {
          /*this.projetsSharedCollection = [];*/
          this.projetsSharedCollection = [this.projetsSharedCollection[i]];
          this.projetSelected = true;
          break;
        }
      }
    }
  }

  save(): void {
    this.isSaving = true;
    const avancement = this.avancementFormService.getAvancement(this.editForm);

    const codeProjet = avancement.code;
    let verif = false;

    if (this.projetSelected) {
      if (codeProjet == this.projetsSharedCollection[0].code) {
        verif = true;
        avancement.projet = this.projetsSharedCollection[0];
        this.sessionStorageService.clear('projetId');
      }
    }
    if (verif) {
      if (avancement.id !== null) {
        this.subscribeToSaveResponse(this.avancementService.update(avancement));
      } else {
        avancement.user = this.user;
        this.subscribeToSaveResponse(this.avancementService.create(avancement));
      }
    } else {
      this.isSaving = false;
      this.onErrorCodeProjet();
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvancement>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(avancement: IAvancement): void {
    this.avancement = avancement;
    this.avancementFormService.resetForm(this.editForm, avancement);

    this.projetsSharedCollection = this.projetService.addProjetToCollectionIfMissing<IProjet>(
      this.projetsSharedCollection,
      avancement.projet
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projetService
      .query()
      .pipe(map((res: HttpResponse<IProjet[]>) => res.body ?? []))
      .pipe(map((projets: IProjet[]) => this.projetService.addProjetToCollectionIfMissing<IProjet>(projets, this.avancement?.projet)))
      .subscribe((projets: IProjet[]) => {
        this.projetsSharedCollection = projets;
        this.loadProjectSelected();
      });
  }

  protected onErrorCodeProjet(): void {
    const Toast = Swal.mixin({
      toast: true,
      position: 'top-end',
      showConfirmButton: false,
      timer: 3000,
      timerProgressBar: true,
      didOpen: toast => {
        toast.addEventListener('mouseenter', Swal.stopTimer);
        toast.addEventListener('mouseleave', Swal.resumeTimer);
      },
    });

    Toast.fire({
      icon: 'error',
      title: 'Le code est incorrect',
    });
  }
}
