import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProjet, NewProjet } from '../projet.model';
import { IAvancement } from '../../avancement/avancement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProjet for edit and NewProjetFormGroupInput for create.
 */
type ProjetFormGroupInput = IProjet | PartialWithRequiredKeyOf<NewProjet>;

type ProjetFormDefaults = Pick<NewProjet, 'id'>;

type ProjetFormGroupContent = {
  id: FormControl<IProjet['id'] | NewProjet['id']>;
  titre: FormControl<IProjet['titre']>;
  description: FormControl<IProjet['description']>;
  duree: FormControl<IProjet['duree']>;
  uniteDuree: FormControl<IProjet['uniteDuree']>;
  ville: FormControl<IProjet['ville']>;
  code: FormControl<IProjet['code']>;
  fichier1: FormControl<IProjet['fichier1']>;
  fichier1ContentType: FormControl<IProjet['fichier1ContentType']>;
  fichier2: FormControl<IProjet['fichier2']>;
  fichier2ContentType: FormControl<IProjet['fichier2ContentType']>;
  fichier3: FormControl<IProjet['fichier3']>;
  fichier3ContentType: FormControl<IProjet['fichier3ContentType']>;
  fichier4: FormControl<IProjet['fichier4']>;
  fichier4ContentType: FormControl<IProjet['fichier4ContentType']>;
  isDone: FormControl<IProjet['isDone']>;
  user: FormControl<IProjet['user']>;
};

export type ProjetFormGroup = FormGroup<ProjetFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProjetFormService {
  createProjetFormGroup(projet: ProjetFormGroupInput = { id: null }): ProjetFormGroup {
    const projetRawValue = {
      ...this.getFormDefaults(),
      ...projet,
    };
    return new FormGroup<ProjetFormGroupContent>({
      id: new FormControl(
        { value: projetRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      titre: new FormControl(projetRawValue.titre, {
        validators: [Validators.required],
      }),
      description: new FormControl(projetRawValue.description),
      duree: new FormControl(projetRawValue.duree, {
        validators: [Validators.required, Validators.min(1)],
      }),
      uniteDuree: new FormControl(projetRawValue.uniteDuree, {
        validators: [Validators.required],
      }),
      ville: new FormControl(projetRawValue.ville, {
        validators: [Validators.required],
      }),
      code: new FormControl(projetRawValue.code, {
        validators: [Validators.required, Validators.minLength(4)],
      }),
      fichier1: new FormControl(projetRawValue.fichier1, {
        validators: [Validators.required],
      }),
      fichier1ContentType: new FormControl(projetRawValue.fichier1ContentType, {
        validators: [Validators.required],
      }),
      fichier2: new FormControl(projetRawValue.fichier2),
      fichier2ContentType: new FormControl(projetRawValue.fichier2ContentType),
      fichier3: new FormControl(projetRawValue.fichier3),
      fichier3ContentType: new FormControl(projetRawValue.fichier3ContentType),
      fichier4: new FormControl(projetRawValue.fichier4),
      fichier4ContentType: new FormControl(projetRawValue.fichier4ContentType),
      isDone: new FormControl(projetRawValue.isDone),
      user: new FormControl(projetRawValue.user),
    });
  }

  getProjet(form: ProjetFormGroup): IProjet | NewProjet {
    return form.getRawValue() as IProjet | NewProjet;
  }

  resetForm(form: ProjetFormGroup, projet: ProjetFormGroupInput): void {
    const projetRawValue = { ...this.getFormDefaults(), ...projet };
    form.reset(
      {
        ...projetRawValue,
        id: { value: projetRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProjetFormDefaults {
    return {
      id: null,
    };
  }
}
