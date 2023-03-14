import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../regle.test-samples';

import { RegleFormService } from './regle-form.service';

describe('Regle Form Service', () => {
  let service: RegleFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegleFormService);
  });

  describe('Service methods', () => {
    describe('createRegleFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRegleFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            texte: expect.any(Object),
            date: expect.any(Object),
            projet: expect.any(Object),
          })
        );
      });

      it('passing IRegle should create a new form with FormGroup', () => {
        const formGroup = service.createRegleFormGroup(sampleWithRequiredData);

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

    describe('getRegle', () => {
      it('should return NewRegle for default Regle initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRegleFormGroup(sampleWithNewData);

        const regle = service.getRegle(formGroup) as any;

        expect(regle).toMatchObject(sampleWithNewData);
      });

      it('should return NewRegle for empty Regle initial value', () => {
        const formGroup = service.createRegleFormGroup();

        const regle = service.getRegle(formGroup) as any;

        expect(regle).toMatchObject({});
      });

      it('should return IRegle', () => {
        const formGroup = service.createRegleFormGroup(sampleWithRequiredData);

        const regle = service.getRegle(formGroup) as any;

        expect(regle).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRegle should not enable id FormControl', () => {
        const formGroup = service.createRegleFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRegle should disable id FormControl', () => {
        const formGroup = service.createRegleFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
