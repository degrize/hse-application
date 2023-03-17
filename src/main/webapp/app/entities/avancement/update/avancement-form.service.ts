import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAvancement, NewAvancement } from '../avancement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAvancement for edit and NewAvancementFormGroupInput for create.
 */
type AvancementFormGroupInput = IAvancement | PartialWithRequiredKeyOf<NewAvancement>;

type AvancementFormDefaults = Pick<NewAvancement, 'id'>;

type AvancementFormGroupContent = {
  id: FormControl<IAvancement['id'] | NewAvancement['id']>;
  description: FormControl<IAvancement['description']>;
  date: FormControl<IAvancement['date']>;
  fichier1: FormControl<IAvancement['fichier1']>;
  fichier1ContentType: FormControl<IAvancement['fichier1ContentType']>;
  fichier2: FormControl<IAvancement['fichier2']>;
  fichier2ContentType: FormControl<IAvancement['fichier2ContentType']>;
  fichier3: FormControl<IAvancement['fichier3']>;
  fichier3ContentType: FormControl<IAvancement['fichier3ContentType']>;
  fichier4: FormControl<IAvancement['fichier4']>;
  fichier4ContentType: FormControl<IAvancement['fichier4ContentType']>;
  projet: FormControl<IAvancement['projet']>;
  user: FormControl<IAvancement['user']>;
  code: FormControl<IAvancement['code']>;
};

export type AvancementFormGroup = FormGroup<AvancementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AvancementFormService {
  createAvancementFormGroup(avancement: AvancementFormGroupInput = { id: null }): AvancementFormGroup {
    const avancementRawValue = {
      ...this.getFormDefaults(),
      ...avancement,
    };
    return new FormGroup<AvancementFormGroupContent>({
      id: new FormControl(
        { value: avancementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      description: new FormControl(avancementRawValue.description, {
        validators: [Validators.required],
      }),
      date: new FormControl(avancementRawValue.date, {
        validators: [Validators.required],
      }),
      fichier1: new FormControl(avancementRawValue.fichier1),
      fichier1ContentType: new FormControl(avancementRawValue.fichier1ContentType),
      fichier2: new FormControl(avancementRawValue.fichier2),
      fichier2ContentType: new FormControl(avancementRawValue.fichier2ContentType),
      fichier3: new FormControl(avancementRawValue.fichier3),
      fichier3ContentType: new FormControl(avancementRawValue.fichier3ContentType),
      fichier4: new FormControl(avancementRawValue.fichier4),
      fichier4ContentType: new FormControl(avancementRawValue.fichier4ContentType),
      projet: new FormControl(avancementRawValue.projet),
      user: new FormControl(avancementRawValue.user),
      code: new FormControl(avancementRawValue.code),
    });
  }

  getAvancement(form: AvancementFormGroup): IAvancement | NewAvancement {
    return form.getRawValue() as IAvancement | NewAvancement;
  }

  resetForm(form: AvancementFormGroup, avancement: AvancementFormGroupInput): void {
    const avancementRawValue = { ...this.getFormDefaults(), ...avancement };
    form.reset(
      {
        ...avancementRawValue,
        id: { value: avancementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AvancementFormDefaults {
    return {
      id: null,
    };
  }
}
