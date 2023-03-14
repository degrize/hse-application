import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRegle } from '../regle.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../regle.test-samples';

import { RegleService, RestRegle } from './regle.service';

const requireRestSample: RestRegle = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.toJSON(),
};

describe('Regle Service', () => {
  let service: RegleService;
  let httpMock: HttpTestingController;
  let expectedResult: IRegle | IRegle[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RegleService);
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

    it('should create a Regle', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const regle = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(regle).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Regle', () => {
      const regle = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(regle).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Regle', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Regle', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Regle', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRegleToCollectionIfMissing', () => {
      it('should add a Regle to an empty array', () => {
        const regle: IRegle = sampleWithRequiredData;
        expectedResult = service.addRegleToCollectionIfMissing([], regle);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(regle);
      });

      it('should not add a Regle to an array that contains it', () => {
        const regle: IRegle = sampleWithRequiredData;
        const regleCollection: IRegle[] = [
          {
            ...regle,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRegleToCollectionIfMissing(regleCollection, regle);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Regle to an array that doesn't contain it", () => {
        const regle: IRegle = sampleWithRequiredData;
        const regleCollection: IRegle[] = [sampleWithPartialData];
        expectedResult = service.addRegleToCollectionIfMissing(regleCollection, regle);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(regle);
      });

      it('should add only unique Regle to an array', () => {
        const regleArray: IRegle[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const regleCollection: IRegle[] = [sampleWithRequiredData];
        expectedResult = service.addRegleToCollectionIfMissing(regleCollection, ...regleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const regle: IRegle = sampleWithRequiredData;
        const regle2: IRegle = sampleWithPartialData;
        expectedResult = service.addRegleToCollectionIfMissing([], regle, regle2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(regle);
        expect(expectedResult).toContain(regle2);
      });

      it('should accept null and undefined values', () => {
        const regle: IRegle = sampleWithRequiredData;
        expectedResult = service.addRegleToCollectionIfMissing([], null, regle, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(regle);
      });

      it('should return initial array if no Regle is added', () => {
        const regleCollection: IRegle[] = [sampleWithRequiredData];
        expectedResult = service.addRegleToCollectionIfMissing(regleCollection, undefined, null);
        expect(expectedResult).toEqual(regleCollection);
      });
    });

    describe('compareRegle', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRegle(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRegle(entity1, entity2);
        const compareResult2 = service.compareRegle(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRegle(entity1, entity2);
        const compareResult2 = service.compareRegle(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRegle(entity1, entity2);
        const compareResult2 = service.compareRegle(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
