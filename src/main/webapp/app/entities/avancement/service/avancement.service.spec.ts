import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IAvancement } from '../avancement.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../avancement.test-samples';

import { AvancementService, RestAvancement } from './avancement.service';

const requireRestSample: RestAvancement = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.format(DATE_FORMAT),
};

describe('Avancement Service', () => {
  let service: AvancementService;
  let httpMock: HttpTestingController;
  let expectedResult: IAvancement | IAvancement[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AvancementService);
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

    it('should create a Avancement', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const avancement = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(avancement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Avancement', () => {
      const avancement = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(avancement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Avancement', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Avancement', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Avancement', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAvancementToCollectionIfMissing', () => {
      it('should add a Avancement to an empty array', () => {
        const avancement: IAvancement = sampleWithRequiredData;
        expectedResult = service.addAvancementToCollectionIfMissing([], avancement);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(avancement);
      });

      it('should not add a Avancement to an array that contains it', () => {
        const avancement: IAvancement = sampleWithRequiredData;
        const avancementCollection: IAvancement[] = [
          {
            ...avancement,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAvancementToCollectionIfMissing(avancementCollection, avancement);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Avancement to an array that doesn't contain it", () => {
        const avancement: IAvancement = sampleWithRequiredData;
        const avancementCollection: IAvancement[] = [sampleWithPartialData];
        expectedResult = service.addAvancementToCollectionIfMissing(avancementCollection, avancement);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(avancement);
      });

      it('should add only unique Avancement to an array', () => {
        const avancementArray: IAvancement[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const avancementCollection: IAvancement[] = [sampleWithRequiredData];
        expectedResult = service.addAvancementToCollectionIfMissing(avancementCollection, ...avancementArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const avancement: IAvancement = sampleWithRequiredData;
        const avancement2: IAvancement = sampleWithPartialData;
        expectedResult = service.addAvancementToCollectionIfMissing([], avancement, avancement2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(avancement);
        expect(expectedResult).toContain(avancement2);
      });

      it('should accept null and undefined values', () => {
        const avancement: IAvancement = sampleWithRequiredData;
        expectedResult = service.addAvancementToCollectionIfMissing([], null, avancement, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(avancement);
      });

      it('should return initial array if no Avancement is added', () => {
        const avancementCollection: IAvancement[] = [sampleWithRequiredData];
        expectedResult = service.addAvancementToCollectionIfMissing(avancementCollection, undefined, null);
        expect(expectedResult).toEqual(avancementCollection);
      });
    });

    describe('compareAvancement', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAvancement(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAvancement(entity1, entity2);
        const compareResult2 = service.compareAvancement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAvancement(entity1, entity2);
        const compareResult2 = service.compareAvancement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAvancement(entity1, entity2);
        const compareResult2 = service.compareAvancement(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
