import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { RegleFormService, RegleFormGroup } from './regle-form.service';
import { IRegle, NewRegle } from '../regle.model';
import { RegleService } from '../service/regle.service';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';
import { SessionStorageService } from 'ngx-webstorage';
import Swal from 'sweetalert2';

@Component({
  selector: 'jhi-regle-update',
  templateUrl: './regle-update.component.html',
})
export class RegleUpdateComponent implements OnInit {
  isSaving = false;
  regle: IRegle | null = null;
  projetSelected = false;

  projetsSharedCollection: IProjet[] = [];

  editForm: RegleFormGroup = this.regleFormService.createRegleFormGroup();

  constructor(
    protected regleService: RegleService,
    protected regleFormService: RegleFormService,
    protected projetService: ProjetService,
    protected activatedRoute: ActivatedRoute,
    private sessionStorageService: SessionStorageService,
    private router: Router
  ) {}

  compareProjet = (o1: IProjet | null, o2: IProjet | null): boolean => this.projetService.compareProjet(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ regle }) => {
      this.regle = regle;
      if (regle) {
        this.updateForm(regle);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  loadProjectSelected(): void {
    if (this.sessionStorageService.retrieve('projetId')) {
      for (let i = 0; i < this.projetsSharedCollection.length; i++) {
        if (this.sessionStorageService.retrieve('projetId') == this.projetsSharedCollection[i].id) {
          this.projetsSharedCollection = [];
          this.projetsSharedCollection.push(this.projetsSharedCollection[i]);
          this.projetSelected = true;
          break;
        }
      }
    }
  }

  save(): void {
    this.isSaving = true;
    const regle = this.regleFormService.getRegle(this.editForm);
    const codeProjet = regle.code;
    let verif = false;

    if (this.projetSelected) {
      if (codeProjet == this.projetsSharedCollection[0].code) {
        verif = true;
      }
    }
    if (verif) {
      if (regle.id !== null) {
        this.subscribeToSaveResponse(this.regleService.update(regle));
      } else {
        this.subscribeToSaveResponse(this.regleService.create(regle));
      }
    } else {
      this.onErrorCodeProjet();
    }
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegle>>): void {
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

  protected updateForm(regle: IRegle): void {
    this.regle = regle;
    this.regleFormService.resetForm(this.editForm, regle);

    this.projetsSharedCollection = this.projetService.addProjetToCollectionIfMissing<IProjet>(this.projetsSharedCollection, regle.projet);
  }

  protected loadRelationshipsOptions(): void {
    this.projetService
      .query()
      .pipe(map((res: HttpResponse<IProjet[]>) => res.body ?? []))
      .pipe(map((projets: IProjet[]) => this.projetService.addProjetToCollectionIfMissing<IProjet>(projets, this.regle?.projet)))
      .subscribe((projets: IProjet[]) => {
        this.projetsSharedCollection = projets;
        this.loadProjectSelected();
      });
  }
}
