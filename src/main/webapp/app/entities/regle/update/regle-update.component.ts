import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { RegleFormService, RegleFormGroup } from './regle-form.service';
import { IRegle } from '../regle.model';
import { RegleService } from '../service/regle.service';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';

@Component({
  selector: 'jhi-regle-update',
  templateUrl: './regle-update.component.html',
})
export class RegleUpdateComponent implements OnInit {
  isSaving = false;
  regle: IRegle | null = null;

  projetsSharedCollection: IProjet[] = [];

  editForm: RegleFormGroup = this.regleFormService.createRegleFormGroup();

  constructor(
    protected regleService: RegleService,
    protected regleFormService: RegleFormService,
    protected projetService: ProjetService,
    protected activatedRoute: ActivatedRoute
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

  save(): void {
    this.isSaving = true;
    const regle = this.regleFormService.getRegle(this.editForm);
    if (regle.id !== null) {
      this.subscribeToSaveResponse(this.regleService.update(regle));
    } else {
      this.subscribeToSaveResponse(this.regleService.create(regle));
    }
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
      .subscribe((projets: IProjet[]) => (this.projetsSharedCollection = projets));
  }
}
