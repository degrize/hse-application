import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SignalementFormService } from './signalement-form.service';
import { SignalementService } from '../service/signalement.service';
import { ISignalement } from '../signalement.model';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';

import { SignalementUpdateComponent } from './signalement-update.component';

describe('Signalement Management Update Component', () => {
  let comp: SignalementUpdateComponent;
  let fixture: ComponentFixture<SignalementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let signalementFormService: SignalementFormService;
  let signalementService: SignalementService;
  let projetService: ProjetService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SignalementUpdateComponent],
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
      .overrideTemplate(SignalementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SignalementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    signalementFormService = TestBed.inject(SignalementFormService);
    signalementService = TestBed.inject(SignalementService);
    projetService = TestBed.inject(ProjetService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Projet query and add missing value', () => {
      const signalement: ISignalement = { id: 456 };
      const projet: IProjet = { id: 95838 };
      signalement.projet = projet;

      const projetCollection: IProjet[] = [{ id: 3816 }];
      jest.spyOn(projetService, 'query').mockReturnValue(of(new HttpResponse({ body: projetCollection })));
      const additionalProjets = [projet];
      const expectedCollection: IProjet[] = [...additionalProjets, ...projetCollection];
      jest.spyOn(projetService, 'addProjetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ signalement });
      comp.ngOnInit();

      expect(projetService.query).toHaveBeenCalled();
      expect(projetService.addProjetToCollectionIfMissing).toHaveBeenCalledWith(
        projetCollection,
        ...additionalProjets.map(expect.objectContaining)
      );
      expect(comp.projetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const signalement: ISignalement = { id: 456 };
      const projet: IProjet = { id: 19122 };
      signalement.projet = projet;

      activatedRoute.data = of({ signalement });
      comp.ngOnInit();

      expect(comp.projetsSharedCollection).toContain(projet);
      expect(comp.signalement).toEqual(signalement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISignalement>>();
      const signalement = { id: 123 };
      jest.spyOn(signalementFormService, 'getSignalement').mockReturnValue(signalement);
      jest.spyOn(signalementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ signalement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: signalement }));
      saveSubject.complete();

      // THEN
      expect(signalementFormService.getSignalement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(signalementService.update).toHaveBeenCalledWith(expect.objectContaining(signalement));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISignalement>>();
      const signalement = { id: 123 };
      jest.spyOn(signalementFormService, 'getSignalement').mockReturnValue({ id: null });
      jest.spyOn(signalementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ signalement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: signalement }));
      saveSubject.complete();

      // THEN
      expect(signalementFormService.getSignalement).toHaveBeenCalled();
      expect(signalementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISignalement>>();
      const signalement = { id: 123 };
      jest.spyOn(signalementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ signalement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(signalementService.update).toHaveBeenCalled();
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
