import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../signalement.test-samples';

import { SignalementFormService } from './signalement-form.service';

describe('Signalement Form Service', () => {
  let service: SignalementFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SignalementFormService);
  });

  describe('Service methods', () => {
    describe('createSignalementFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSignalementFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            texte: expect.any(Object),
            date: expect.any(Object),
            projet: expect.any(Object),
          })
        );
      });

      it('passing ISignalement should create a new form with FormGroup', () => {
        const formGroup = service.createSignalementFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            texte: expect.any(Object),
            date: expect.any(Object),
            projet: expect.any(Object),
          })
        );
      });
    });

    describe('getSignalement', () => {
      it('should return NewSignalement for default Signalement initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSignalementFormGroup(sampleWithNewData);

        const signalement = service.getSignalement(formGroup) as any;

        expect(signalement).toMatchObject(sampleWithNewData);
      });

      it('should return NewSignalement for empty Signalement initial value', () => {
        const formGroup = service.createSignalementFormGroup();

        const signalement = service.getSignalement(formGroup) as any;

        expect(signalement).toMatchObject({});
      });

      it('should return ISignalement', () => {
        const formGroup = service.createSignalementFormGroup(sampleWithRequiredData);

        const signalement = service.getSignalement(formGroup) as any;

        expect(signalement).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISignalement should not enable id FormControl', () => {
        const formGroup = service.createSignalementFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSignalement should disable id FormControl', () => {
        const formGroup = service.createSignalementFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
