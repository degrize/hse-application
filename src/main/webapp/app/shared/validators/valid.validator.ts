import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function numeroCIValidator(): ValidatorFn {
  return (ctrl: AbstractControl): null | ValidationErrors => {
    if (ctrl.value) {
      if (ctrl.value.toString().startsWith('05') || ctrl.value.toString().startsWith('07') || ctrl.value.toString().startsWith('01')) {
        return null;
      }
      return {
        validValidator: ctrl.value,
      };
    } else {
      return {
        validValidator: ctrl.value,
      };
    }
  };
}
