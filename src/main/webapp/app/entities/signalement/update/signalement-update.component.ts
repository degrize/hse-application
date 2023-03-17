import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SignalementFormService, SignalementFormGroup } from './signalement-form.service';
import { ISignalement } from '../signalement.model';
import { SignalementService } from '../service/signalement.service';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';
import { IUser } from '../../user/user.model';
import { User } from '../../../admin/user-management/user-management.model';
import { AccountService } from '../../../core/auth/account.service';
import { UserManagementService } from '../../../admin/user-management/service/user-management.service';
import { SessionStorageService } from 'ngx-webstorage';

@Component({
  selector: 'jhi-signalement-update',
  templateUrl: './signalement-update.component.html',
})
export class SignalementUpdateComponent implements OnInit {
  isSaving = false;
  signalement: ISignalement | null = null;

  projetsSharedCollection: IProjet[] = [];

  editForm: SignalementFormGroup = this.signalementFormService.createSignalementFormGroup();

  user: IUser = { id: 1, login: '' };
  projetSelected = false;

  constructor(
    protected signalementService: SignalementService,
    protected signalementFormService: SignalementFormService,
    protected projetService: ProjetService,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService,
    private userManagementService: UserManagementService,
    private sessionStorageService: SessionStorageService
  ) {}

  compareProjet = (o1: IProjet | null, o2: IProjet | null): boolean => this.projetService.compareProjet(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ signalement }) => {
      this.signalement = signalement;
      if (signalement) {
        this.updateForm(signalement);
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
                alert(res.id);
              }
            },
            error: () => 'ERREUR',
          });
        }
      }
    });
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
    const signalement = this.signalementFormService.getSignalement(this.editForm);

    let verif = false;

    if (this.projetSelected) {
      verif = true;
      signalement.projet = this.projetsSharedCollection[0];
      this.sessionStorageService.clear('projetId');
    }
    if (verif) {
      if (signalement.id !== null) {
        this.subscribeToSaveResponse(this.signalementService.update(signalement));
      } else {
        signalement.user = this.user;
        this.subscribeToSaveResponse(this.signalementService.create(signalement));
      }
    } else {
      this.isSaving = false;
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISignalement>>): void {
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

  protected updateForm(signalement: ISignalement): void {
    this.signalement = signalement;
    this.signalementFormService.resetForm(this.editForm, signalement);

    this.projetsSharedCollection = this.projetService.addProjetToCollectionIfMissing<IProjet>(
      this.projetsSharedCollection,
      signalement.projet
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projetService
      .query()
      .pipe(map((res: HttpResponse<IProjet[]>) => res.body ?? []))
      .pipe(map((projets: IProjet[]) => this.projetService.addProjetToCollectionIfMissing<IProjet>(projets, this.signalement?.projet)))
      .subscribe((projets: IProjet[]) => {
        this.projetsSharedCollection = projets;
        this.loadProjectSelected();
      });
  }
}
