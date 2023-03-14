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

@Component({
  selector: 'jhi-signalement-update',
  templateUrl: './signalement-update.component.html',
})
export class SignalementUpdateComponent implements OnInit {
  isSaving = false;
  signalement: ISignalement | null = null;

  projetsSharedCollection: IProjet[] = [];

  editForm: SignalementFormGroup = this.signalementFormService.createSignalementFormGroup();

  constructor(
    protected signalementService: SignalementService,
    protected signalementFormService: SignalementFormService,
    protected projetService: ProjetService,
    protected activatedRoute: ActivatedRoute
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
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const signalement = this.signalementFormService.getSignalement(this.editForm);
    if (signalement.id !== null) {
      this.subscribeToSaveResponse(this.signalementService.update(signalement));
    } else {
      this.subscribeToSaveResponse(this.signalementService.create(signalement));
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
      .subscribe((projets: IProjet[]) => (this.projetsSharedCollection = projets));
  }
}
