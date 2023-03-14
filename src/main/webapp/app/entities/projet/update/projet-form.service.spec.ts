import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../projet.test-samples';

import { ProjetFormService } from './projet-form.service';

describe('Projet Form Service', () => {
  let service: ProjetFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjetFormService);
  });

  describe('Service methods', () => {
    describe('createProjetFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProjetFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titre: expect.any(Object),
            description: expect.any(Object),
            duree: expect.any(Object),
            ville: expect.any(Object),
            code: expect.any(Object),
            fichier1: expect.any(Object),
            fichier2: expect.any(Object),
            fichier3: expect.any(Object),
            fichier4: expect.any(Object),
          })
        );
      });

      it('passing IProjet should create a new form with FormGroup', () => {
        const formGroup = service.createProjetFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titre: expect.any(Object),
            description: expect.any(Object),
            duree: expect.any(Object),
            ville: expect.any(Object),
            code: expect.any(Object),
            fichier1: expect.any(Object),
            fichier2: expect.any(Object),
            fichier3: expect.any(Object),
            fichier4: expect.any(Object),
          })
        );
      });
    });

    describe('getProjet', () => {
      it('should return NewProjet for default Projet initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProjetFormGroup(sampleWithNewData);

        const projet = service.getProjet(formGroup) as any;

        expect(projet).toMatchObject(sampleWithNewData);
      });

      it('should return NewProjet for empty Projet initial value', () => {
        const formGroup = service.createProjetFormGroup();

        const projet = service.getProjet(formGroup) as any;

        expect(projet).toMatchObject({});
      });

      it('should return IProjet', () => {
        const formGroup = service.createProjetFormGroup(sampleWithRequiredData);

        const projet = service.getProjet(formGroup) as any;

        expect(projet).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProjet should not enable id FormControl', () => {
        const formGroup = service.createProjetFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProjet should disable id FormControl', () => {
        const formGroup = service.createProjetFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
