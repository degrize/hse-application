import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RegleFormService } from './regle-form.service';
import { RegleService } from '../service/regle.service';
import { IRegle } from '../regle.model';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';

import { RegleUpdateComponent } from './regle-update.component';

describe('Regle Management Update Component', () => {
  let comp: RegleUpdateComponent;
  let fixture: ComponentFixture<RegleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let regleFormService: RegleFormService;
  let regleService: RegleService;
  let projetService: ProjetService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RegleUpdateComponent],
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
      .overrideTemplate(RegleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RegleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    regleFormService = TestBed.inject(RegleFormService);
    regleService = TestBed.inject(RegleService);
    projetService = TestBed.inject(ProjetService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Projet query and add missing value', () => {
      const regle: IRegle = { id: 456 };
      const projet: IProjet = { id: 96892 };
      regle.projet = projet;

      const projetCollection: IProjet[] = [{ id: 67773 }];
      jest.spyOn(projetService, 'query').mockReturnValue(of(new HttpResponse({ body: projetCollection })));
      const additionalProjets = [projet];
      const expectedCollection: IProjet[] = [...additionalProjets, ...projetCollection];
      jest.spyOn(projetService, 'addProjetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ regle });
      comp.ngOnInit();

      expect(projetService.query).toHaveBeenCalled();
      expect(projetService.addProjetToCollectionIfMissing).toHaveBeenCalledWith(
        projetCollection,
        ...additionalProjets.map(expect.objectContaining)
      );
      expect(comp.projetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const regle: IRegle = { id: 456 };
      const projet: IProjet = { id: 12917 };
      regle.projet = projet;

      activatedRoute.data = of({ regle });
      comp.ngOnInit();

      expect(comp.projetsSharedCollection).toContain(projet);
      expect(comp.regle).toEqual(regle);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRegle>>();
      const regle = { id: 123 };
      jest.spyOn(regleFormService, 'getRegle').mockReturnValue(regle);
      jest.spyOn(regleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ regle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: regle }));
      saveSubject.complete();

      // THEN
      expect(regleFormService.getRegle).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(regleService.update).toHaveBeenCalledWith(expect.objectContaining(regle));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRegle>>();
      const regle = { id: 123 };
      jest.spyOn(regleFormService, 'getRegle').mockReturnValue({ id: null });
      jest.spyOn(regleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ regle: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: regle }));
      saveSubject.complete();

      // THEN
      expect(regleFormService.getRegle).toHaveBeenCalled();
      expect(regleService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRegle>>();
      const regle = { id: 123 };
      jest.spyOn(regleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ regle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(regleService.update).toHaveBeenCalled();
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
