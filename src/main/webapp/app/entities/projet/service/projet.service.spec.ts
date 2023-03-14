import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProjet } from '../projet.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../projet.test-samples';

import { ProjetService } from './projet.service';

const requireRestSample: IProjet = {
  ...sampleWithRequiredData,
};

describe('Projet Service', () => {
  let service: ProjetService;
  let httpMock: HttpTestingController;
  let expectedResult: IProjet | IProjet[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProjetService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Projet', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const projet = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(projet).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Projet', () => {
      const projet = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(projet).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Projet', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Projet', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Projet', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProjetToCollectionIfMissing', () => {
      it('should add a Projet to an empty array', () => {
        const projet: IProjet = sampleWithRequiredData;
        expectedResult = service.addProjetToCollectionIfMissing([], projet);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projet);
      });

      it('should not add a Projet to an array that contains it', () => {
        const projet: IProjet = sampleWithRequiredData;
        const projetCollection: IProjet[] = [
          {
            ...projet,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProjetToCollectionIfMissing(projetCollection, projet);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Projet to an array that doesn't contain it", () => {
        const projet: IProjet = sampleWithRequiredData;
        const projetCollection: IProjet[] = [sampleWithPartialData];
        expectedResult = service.addProjetToCollectionIfMissing(projetCollection, projet);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projet);
      });

      it('should add only unique Projet to an array', () => {
        const projetArray: IProjet[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const projetCollection: IProjet[] = [sampleWithRequiredData];
        expectedResult = service.addProjetToCollectionIfMissing(projetCollection, ...projetArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const projet: IProjet = sampleWithRequiredData;
        const projet2: IProjet = sampleWithPartialData;
        expectedResult = service.addProjetToCollectionIfMissing([], projet, projet2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projet);
        expect(expectedResult).toContain(projet2);
      });

      it('should accept null and undefined values', () => {
        const projet: IProjet = sampleWithRequiredData;
        expectedResult = service.addProjetToCollectionIfMissing([], null, projet, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projet);
      });

      it('should return initial array if no Projet is added', () => {
        const projetCollection: IProjet[] = [sampleWithRequiredData];
        expectedResult = service.addProjetToCollectionIfMissing(projetCollection, undefined, null);
        expect(expectedResult).toEqual(projetCollection);
      });
    });

    describe('compareProjet', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProjet(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProjet(entity1, entity2);
        const compareResult2 = service.compareProjet(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProjet(entity1, entity2);
        const compareResult2 = service.compareProjet(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProjet(entity1, entity2);
        const compareResult2 = service.compareProjet(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
