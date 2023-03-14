import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AvancementFormService } from './avancement-form.service';
import { AvancementService } from '../service/avancement.service';
import { IAvancement } from '../avancement.model';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';

import { AvancementUpdateComponent } from './avancement-update.component';

describe('Avancement Management Update Component', () => {
  let comp: AvancementUpdateComponent;
  let fixture: ComponentFixture<AvancementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let avancementFormService: AvancementFormService;
  let avancementService: AvancementService;
  let projetService: ProjetService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AvancementUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AvancementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AvancementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    avancementFormService = TestBed.inject(AvancementFormService);
    avancementService = TestBed.inject(AvancementService);
    projetService = TestBed.inject(ProjetService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Projet query and add missing value', () => {
      const avancement: IAvancement = { id: 456 };
      const projet: IProjet = { id: 31795 };
      avancement.projet = projet;

      const projetCollection: IProjet[] = [{ id: 66854 }];
      jest.spyOn(projetService, 'query').mockReturnValue(of(new HttpResponse({ body: projetCollection })));
      const additionalProjets = [projet];
      const expectedCollection: IProjet[] = [...additionalProjets, ...projetCollection];
      jest.spyOn(projetService, 'addProjetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ avancement });
      comp.ngOnInit();

      expect(projetService.query).toHaveBeenCalled();
      expect(projetService.addProjetToCollectionIfMissing).toHaveBeenCalledWith(
        projetCollection,
        ...additionalProjets.map(expect.objectContaining)
      );
      expect(comp.projetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const avancement: IAvancement = { id: 456 };
      const projet: IProjet = { id: 7580 };
      avancement.projet = projet;

      activatedRoute.data = of({ avancement });
      comp.ngOnInit();

      expect(comp.projetsSharedCollection).toContain(projet);
      expect(comp.avancement).toEqual(avancement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvancement>>();
      const avancement = { id: 123 };
      jest.spyOn(avancementFormService, 'getAvancement').mockReturnValue(avancement);
      jest.spyOn(avancementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avancement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: avancement }));
      saveSubject.complete();

      // THEN
      expect(avancementFormService.getAvancement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(avancementService.update).toHaveBeenCalledWith(expect.objectContaining(avancement));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvancement>>();
      const avancement = { id: 123 };
      jest.spyOn(avancementFormService, 'getAvancement').mockReturnValue({ id: null });
      jest.spyOn(avancementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avancement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: avancement }));
      saveSubject.complete();

      // THEN
      expect(avancementFormService.getAvancement).toHaveBeenCalled();
      expect(avancementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAvancement>>();
      const avancement = { id: 123 };
      jest.spyOn(avancementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ avancement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(avancementService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProjet', () => {
      it('Should forward to projetService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(projetService, 'compareProjet');
        comp.compareProjet(entity, entity2);
        expect(projetService.compareProjet).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
