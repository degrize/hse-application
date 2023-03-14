import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISignalement, NewSignalement } from '../signalement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISignalement for edit and NewSignalementFormGroupInput for create.
 */
type SignalementFormGroupInput = ISignalement | PartialWithRequiredKeyOf<NewSignalement>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISignalement | NewSignalement> = Omit<T, 'date'> & {
  date?: string | null;
};

type SignalementFormRawValue = FormValueOf<ISignalement>;

type NewSignalementFormRawValue = FormValueOf<NewSignalement>;

type SignalementFormDefaults = Pick<NewSignalement, 'id' | 'date'>;

type SignalementFormGroupContent = {
  id: FormControl<SignalementFormRawValue['id'] | NewSignalement['id']>;
  texte: FormControl<SignalementFormRawValue['texte']>;
  date: FormControl<SignalementFormRawValue['date']>;
  projet: FormControl<SignalementFormRawValue['projet']>;
};

export type SignalementFormGroup = FormGroup<SignalementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SignalementFormService {
  createSignalementFormGroup(signalement: SignalementFormGroupInput = { id: null }): SignalementFormGroup {
    const signalementRawValue = this.convertSignalementToSignalementRawValue({
      ...this.getFormDefaults(),
      ...signalement,
    });
    return new FormGroup<SignalementFormGroupContent>({
      id: new FormControl(
        { value: signalementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      texte: new FormControl(signalementRawValue.texte, {
        validators: [Validators.required],
      }),
      date: new FormControl(signalementRawValue.date, {
        validators: [Validators.required],
      }),
      projet: new FormControl(signalementRawValue.projet),
    });
  }

  getSignalement(form: SignalementFormGroup): ISignalement | NewSignalement {
    return this.convertSignalementRawValueToSignalement(form.getRawValue() as SignalementFormRawValue | NewSignalementFormRawValue);
  }

  resetForm(form: SignalementFormGroup, signalement: SignalementFormGroupInput): void {
    const signalementRawValue = this.convertSignalementToSignalementRawValue({ ...this.getFormDefaults(), ...signalement });
    form.reset(
      {
        ...signalementRawValue,
        id: { value: signalementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SignalementFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      date: currentTime,
    };
  }

  private convertSignalementRawValueToSignalement(
    rawSignalement: SignalementFormRawValue | NewSignalementFormRawValue
  ): ISignalement | NewSignalement {
    return {
      ...rawSignalement,
      date: dayjs(rawSignalement.date, DATE_TIME_FORMAT),
    };
  }

  private convertSignalementToSignalementRawValue(
    signalement: ISignalement | (Partial<NewSignalement> & SignalementFormDefaults)
  ): SignalementFormRawValue | PartialWithRequiredKeyOf<NewSignalementFormRawValue> {
    return {
      ...signalement,
      date: signalement.date ? signalement.date.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
