import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDUnit, DUnit } from '../d-unit.model';

import { DUnitService } from './d-unit.service';

describe('Service Tests', () => {
  describe('DUnit Service', () => {
    let service: DUnitService;
    let httpMock: HttpTestingController;
    let elemDefault: IDUnit;
    let expectedResult: IDUnit | IDUnit[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DUnitService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        unit: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DUnit', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DUnit()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DUnit', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            unit: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DUnit', () => {
        const patchObject = Object.assign(
          {
            unit: 'BBBBBB',
          },
          new DUnit()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DUnit', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            unit: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DUnit', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDUnitToCollectionIfMissing', () => {
        it('should add a DUnit to an empty array', () => {
          const dUnit: IDUnit = { id: 123 };
          expectedResult = service.addDUnitToCollectionIfMissing([], dUnit);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dUnit);
        });

        it('should not add a DUnit to an array that contains it', () => {
          const dUnit: IDUnit = { id: 123 };
          const dUnitCollection: IDUnit[] = [
            {
              ...dUnit,
            },
            { id: 456 },
          ];
          expectedResult = service.addDUnitToCollectionIfMissing(dUnitCollection, dUnit);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DUnit to an array that doesn't contain it", () => {
          const dUnit: IDUnit = { id: 123 };
          const dUnitCollection: IDUnit[] = [{ id: 456 }];
          expectedResult = service.addDUnitToCollectionIfMissing(dUnitCollection, dUnit);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dUnit);
        });

        it('should add only unique DUnit to an array', () => {
          const dUnitArray: IDUnit[] = [{ id: 123 }, { id: 456 }, { id: 78000 }];
          const dUnitCollection: IDUnit[] = [{ id: 123 }];
          expectedResult = service.addDUnitToCollectionIfMissing(dUnitCollection, ...dUnitArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dUnit: IDUnit = { id: 123 };
          const dUnit2: IDUnit = { id: 456 };
          expectedResult = service.addDUnitToCollectionIfMissing([], dUnit, dUnit2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dUnit);
          expect(expectedResult).toContain(dUnit2);
        });

        it('should accept null and undefined values', () => {
          const dUnit: IDUnit = { id: 123 };
          expectedResult = service.addDUnitToCollectionIfMissing([], null, dUnit, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dUnit);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
