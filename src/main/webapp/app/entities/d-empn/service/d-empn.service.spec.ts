import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDEmpn, DEmpn } from '../d-empn.model';

import { DEmpnService } from './d-empn.service';

describe('Service Tests', () => {
  describe('DEmpn Service', () => {
    let service: DEmpnService;
    let httpMock: HttpTestingController;
    let elemDefault: IDEmpn;
    let expectedResult: IDEmpn | IDEmpn[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DEmpnService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        empnid: 0,
        empn: 'AAAAAAA',
        deptid: 0,
        phone: 'AAAAAAA',
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

      it('should create a DEmpn', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DEmpn()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DEmpn', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            empnid: 1,
            empn: 'BBBBBB',
            deptid: 1,
            phone: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DEmpn', () => {
        const patchObject = Object.assign(
          {
            deptid: 1,
          },
          new DEmpn()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DEmpn', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            empnid: 1,
            empn: 'BBBBBB',
            deptid: 1,
            phone: 'BBBBBB',
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

      it('should delete a DEmpn', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDEmpnToCollectionIfMissing', () => {
        it('should add a DEmpn to an empty array', () => {
          const dEmpn: IDEmpn = { id: 123 };
          expectedResult = service.addDEmpnToCollectionIfMissing([], dEmpn);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dEmpn);
        });

        it('should not add a DEmpn to an array that contains it', () => {
          const dEmpn: IDEmpn = { id: 123 };
          const dEmpnCollection: IDEmpn[] = [
            {
              ...dEmpn,
            },
            { id: 456 },
          ];
          expectedResult = service.addDEmpnToCollectionIfMissing(dEmpnCollection, dEmpn);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DEmpn to an array that doesn't contain it", () => {
          const dEmpn: IDEmpn = { id: 123 };
          const dEmpnCollection: IDEmpn[] = [{ id: 456 }];
          expectedResult = service.addDEmpnToCollectionIfMissing(dEmpnCollection, dEmpn);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dEmpn);
        });

        it('should add only unique DEmpn to an array', () => {
          const dEmpnArray: IDEmpn[] = [{ id: 123 }, { id: 456 }, { id: 80810 }];
          const dEmpnCollection: IDEmpn[] = [{ id: 123 }];
          expectedResult = service.addDEmpnToCollectionIfMissing(dEmpnCollection, ...dEmpnArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dEmpn: IDEmpn = { id: 123 };
          const dEmpn2: IDEmpn = { id: 456 };
          expectedResult = service.addDEmpnToCollectionIfMissing([], dEmpn, dEmpn2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dEmpn);
          expect(expectedResult).toContain(dEmpn2);
        });

        it('should accept null and undefined values', () => {
          const dEmpn: IDEmpn = { id: 123 };
          expectedResult = service.addDEmpnToCollectionIfMissing([], null, dEmpn, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dEmpn);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
