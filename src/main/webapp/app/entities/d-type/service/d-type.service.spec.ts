import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDType, DType } from '../d-type.model';

import { DTypeService } from './d-type.service';

describe('Service Tests', () => {
  describe('DType Service', () => {
    let service: DTypeService;
    let httpMock: HttpTestingController;
    let elemDefault: IDType;
    let expectedResult: IDType | IDType[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DTypeService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        typeid: 0,
        typename: 'AAAAAAA',
        fatherid: 0,
        disabled: 0,
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

      it('should create a DType', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DType()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DType', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeid: 1,
            typename: 'BBBBBB',
            fatherid: 1,
            disabled: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DType', () => {
        const patchObject = Object.assign(
          {
            typename: 'BBBBBB',
            disabled: 1,
          },
          new DType()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DType', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeid: 1,
            typename: 'BBBBBB',
            fatherid: 1,
            disabled: 1,
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

      it('should delete a DType', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDTypeToCollectionIfMissing', () => {
        it('should add a DType to an empty array', () => {
          const dType: IDType = { id: 123 };
          expectedResult = service.addDTypeToCollectionIfMissing([], dType);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dType);
        });

        it('should not add a DType to an array that contains it', () => {
          const dType: IDType = { id: 123 };
          const dTypeCollection: IDType[] = [
            {
              ...dType,
            },
            { id: 456 },
          ];
          expectedResult = service.addDTypeToCollectionIfMissing(dTypeCollection, dType);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DType to an array that doesn't contain it", () => {
          const dType: IDType = { id: 123 };
          const dTypeCollection: IDType[] = [{ id: 456 }];
          expectedResult = service.addDTypeToCollectionIfMissing(dTypeCollection, dType);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dType);
        });

        it('should add only unique DType to an array', () => {
          const dTypeArray: IDType[] = [{ id: 123 }, { id: 456 }, { id: 59554 }];
          const dTypeCollection: IDType[] = [{ id: 123 }];
          expectedResult = service.addDTypeToCollectionIfMissing(dTypeCollection, ...dTypeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dType: IDType = { id: 123 };
          const dType2: IDType = { id: 456 };
          expectedResult = service.addDTypeToCollectionIfMissing([], dType, dType2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dType);
          expect(expectedResult).toContain(dType2);
        });

        it('should accept null and undefined values', () => {
          const dType: IDType = { id: 123 };
          expectedResult = service.addDTypeToCollectionIfMissing([], null, dType, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dType);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
