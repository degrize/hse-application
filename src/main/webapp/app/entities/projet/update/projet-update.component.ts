import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ProjetFormService, ProjetFormGroup } from './projet-form.service';
import { IProjet } from '../projet.model';
import { ProjetService } from '../service/projet.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

import villes from '../../../../content/villes.json';
import { IUser } from '../../user/user.model';
import { AccountService } from '../../../core/auth/account.service';
import { UserManagementService } from '../../../admin/user-management/service/user-management.service';
import { User } from '../../../admin/user-management/user-management.model';

@Component({
  selector: 'jhi-projet-update',
  templateUrl: './projet-update.component.html',
  styleUrls: ['./projet-update.component.scss'],
})
export class ProjetUpdateComponent implements OnInit {
  isSaving = false;
  projet: IProjet | null = null;

  editForm: ProjetFormGroup = this.projetFormService.createProjetFormGroup();

  villesList: { ville: string }[] = villes;
  user: IUser = { id: 1, login: '' };

  userId: number | null = null;

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected projetService: ProjetService,
    protected projetFormService: ProjetFormService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService,
    private userManagementService: UserManagementService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projet }) => {
      this.projet = projet;
      if (projet) {
        this.updateForm(projet);
      }
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

  save(): void {
    this.isSaving = true;
    const projet = this.projetFormService.getProjet(this.editForm);
    if (projet.id !== null) {
      this.subscribeToSaveResponse(this.projetService.update(projet));
    } else {
      projet.user = this.user;
      this.subscribeToSaveResponse(this.projetService.create(projet));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjet>>): void {
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

  protected updateForm(projet: IProjet): void {
    this.projet = projet;
    this.projetFormService.resetForm(this.editForm, projet);
  }
}
