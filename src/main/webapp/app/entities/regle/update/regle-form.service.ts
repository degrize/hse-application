import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRegle, NewRegle } from '../regle.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRegle for edit and NewRegleFormGroupInput for create.
 */
type RegleFormGroupInput = IRegle | PartialWithRequiredKeyOf<NewRegle>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IRegle | NewRegle> = Omit<T, 'date'> & {
  date?: string | null;
};

type RegleFormRawValue = FormValueOf<IRegle>;

type NewRegleFormRawValue = FormValueOf<NewRegle>;

type RegleFormDefaults = Pick<NewRegle, 'id' | 'date'>;

type RegleFormGroupContent = {
  id: FormControl<RegleFormRawValue['id'] | NewRegle['id']>;
  texte: FormControl<RegleFormRawValue['texte']>;
  date: FormControl<RegleFormRawValue['date']>;
  projet: FormControl<RegleFormRawValue['projet']>;
  code: FormControl<RegleFormRawValue['code']>;
};

export type RegleFormGroup = FormGroup<RegleFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RegleFormService {
  createRegleFormGroup(regle: RegleFormGroupInput = { id: null }): RegleFormGroup {
    const regleRawValue = this.convertRegleToRegleRawValue({
      ...this.getFormDefaults(),
      ...regle,
    });
    return new FormGroup<RegleFormGroupContent>({
      id: new FormControl(
        { value: regleRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      texte: new FormControl(regleRawValue.texte, {
        validators: [Validators.required],
      }),
      date: new FormControl(regleRawValue.date, {
        validators: [Validators.required],
      }),
      projet: new FormControl(regleRawValue.projet),
      code: new FormControl(regleRawValue.code, {
        validators: [Validators.required],
      }),
    });
  }

  getRegle(form: RegleFormGroup): IRegle | NewRegle {
    return this.convertRegleRawValueToRegle(form.getRawValue() as RegleFormRawValue | NewRegleFormRawValue);
  }

  resetForm(form: RegleFormGroup, regle: RegleFormGroupInput): void {
    const regleRawValue = this.convertRegleToRegleRawValue({ ...this.getFormDefaults(), ...regle });
    form.reset(
      {
        ...regleRawValue,
        id: { value: regleRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RegleFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      date: currentTime,
    };
  }

  private convertRegleRawValueToRegle(rawRegle: RegleFormRawValue | NewRegleFormRawValue): IRegle | NewRegle {
    return {
      ...rawRegle,
      date: dayjs(rawRegle.date, DATE_TIME_FORMAT),
    };
  }

  private convertRegleToRegleRawValue(
    regle: IRegle | (Partial<NewRegle> & RegleFormDefaults)
  ): RegleFormRawValue | PartialWithRequiredKeyOf<NewRegleFormRawValue> {
    return {
      ...regle,
      date: regle.date ? regle.date.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
