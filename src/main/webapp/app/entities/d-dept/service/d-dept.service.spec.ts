import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDDept, DDept } from '../d-dept.model';

import { DDeptService } from './d-dept.service';

describe('Service Tests', () => {
  describe('DDept Service', () => {
    let service: DDeptService;
    let httpMock: HttpTestingController;
    let elemDefault: IDDept;
    let expectedResult: IDDept | IDDept[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DDeptService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        deptid: 0,
        deptname: 'AAAAAAA',
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

      it('should create a DDept', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DDept()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DDept', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            deptid: 1,
            deptname: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DDept', () => {
        const patchObject = Object.assign(
          {
            deptid: 1,
            deptname: 'BBBBBB',
          },
          new DDept()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DDept', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            deptid: 1,
            deptname: 'BBBBBB',
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

      it('should delete a DDept', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDDeptToCollectionIfMissing', () => {
        it('should add a DDept to an empty array', () => {
          const dDept: IDDept = { id: 123 };
          expectedResult = service.addDDeptToCollectionIfMissing([], dDept);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dDept);
        });

        it('should not add a DDept to an array that contains it', () => {
          const dDept: IDDept = { id: 123 };
          const dDeptCollection: IDDept[] = [
            {
              ...dDept,
            },
            { id: 456 },
          ];
          expectedResult = service.addDDeptToCollectionIfMissing(dDeptCollection, dDept);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DDept to an array that doesn't contain it", () => {
          const dDept: IDDept = { id: 123 };
          const dDeptCollection: IDDept[] = [{ id: 456 }];
          expectedResult = service.addDDeptToCollectionIfMissing(dDeptCollection, dDept);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dDept);
        });

        it('should add only unique DDept to an array', () => {
          const dDeptArray: IDDept[] = [{ id: 123 }, { id: 456 }, { id: 79464 }];
          const dDeptCollection: IDDept[] = [{ id: 123 }];
          expectedResult = service.addDDeptToCollectionIfMissing(dDeptCollection, ...dDeptArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dDept: IDDept = { id: 123 };
          const dDept2: IDDept = { id: 456 };
          expectedResult = service.addDDeptToCollectionIfMissing([], dDept, dDept2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dDept);
          expect(expectedResult).toContain(dDept2);
        });

        it('should accept null and undefined values', () => {
          const dDept: IDDept = { id: 123 };
          expectedResult = service.addDDeptToCollectionIfMissing([], null, dDept, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dDept);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
