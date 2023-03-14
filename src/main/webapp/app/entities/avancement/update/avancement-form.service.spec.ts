import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../avancement.test-samples';

import { AvancementFormService } from './avancement-form.service';

describe('Avancement Form Service', () => {
  let service: AvancementFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AvancementFormService);
  });

  describe('Service methods', () => {
    describe('createAvancementFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAvancementFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            description: expect.any(Object),
            date: expect.any(Object),
            fichier1: expect.any(Object),
            fichier2: expect.any(Object),
            fichier3: expect.any(Object),
            fichier4: expect.any(Object),
            projet: expect.any(Object),
          })
        );
      });

      it('passing IAvancement should create a new form with FormGroup', () => {
        const formGroup = service.createAvancementFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            description: expect.any(Object),
            date: expect.any(Object),
            fichier1: expect.any(Object),
            fichier2: expect.any(Object),
            fichier3: expect.any(Object),
            fichier4: expect.any(Object),
            projet: expect.any(Object),
          })
        );
      });
    });

    describe('getAvancement', () => {
      it('should return NewAvancement for default Avancement initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAvancementFormGroup(sampleWithNewData);

        const avancement = service.getAvancement(formGroup) as any;

        expect(avancement).toMatchObject(sampleWithNewData);
      });

      it('should return NewAvancement for empty Avancement initial value', () => {
        const formGroup = service.createAvancementFormGroup();

        const avancement = service.getAvancement(formGroup) as any;

        expect(avancement).toMatchObject({});
      });

      it('should return IAvancement', () => {
        const formGroup = service.createAvancementFormGroup(sampleWithRequiredData);

        const avancement = service.getAvancement(formGroup) as any;

        expect(avancement).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAvancement should not enable id FormControl', () => {
        const formGroup = service.createAvancementFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAvancement should disable id FormControl', () => {
        const formGroup = service.createAvancementFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
