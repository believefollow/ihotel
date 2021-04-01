import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFwEmpn, FwEmpn } from '../fw-empn.model';

import { FwEmpnService } from './fw-empn.service';

describe('Service Tests', () => {
  describe('FwEmpn Service', () => {
    let service: FwEmpnService;
    let httpMock: HttpTestingController;
    let elemDefault: IFwEmpn;
    let expectedResult: IFwEmpn | IFwEmpn[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FwEmpnService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        empnid: 'AAAAAAA',
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

      it('should create a FwEmpn', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new FwEmpn()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FwEmpn', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            empnid: 'BBBBBB',
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

      it('should partial update a FwEmpn', () => {
        const patchObject = Object.assign(
          {
            phone: 'BBBBBB',
          },
          new FwEmpn()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FwEmpn', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            empnid: 'BBBBBB',
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

      it('should delete a FwEmpn', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFwEmpnToCollectionIfMissing', () => {
        it('should add a FwEmpn to an empty array', () => {
          const fwEmpn: IFwEmpn = { id: 123 };
          expectedResult = service.addFwEmpnToCollectionIfMissing([], fwEmpn);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fwEmpn);
        });

        it('should not add a FwEmpn to an array that contains it', () => {
          const fwEmpn: IFwEmpn = { id: 123 };
          const fwEmpnCollection: IFwEmpn[] = [
            {
              ...fwEmpn,
            },
            { id: 456 },
          ];
          expectedResult = service.addFwEmpnToCollectionIfMissing(fwEmpnCollection, fwEmpn);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a FwEmpn to an array that doesn't contain it", () => {
          const fwEmpn: IFwEmpn = { id: 123 };
          const fwEmpnCollection: IFwEmpn[] = [{ id: 456 }];
          expectedResult = service.addFwEmpnToCollectionIfMissing(fwEmpnCollection, fwEmpn);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fwEmpn);
        });

        it('should add only unique FwEmpn to an array', () => {
          const fwEmpnArray: IFwEmpn[] = [{ id: 123 }, { id: 456 }, { id: 97844 }];
          const fwEmpnCollection: IFwEmpn[] = [{ id: 123 }];
          expectedResult = service.addFwEmpnToCollectionIfMissing(fwEmpnCollection, ...fwEmpnArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const fwEmpn: IFwEmpn = { id: 123 };
          const fwEmpn2: IFwEmpn = { id: 456 };
          expectedResult = service.addFwEmpnToCollectionIfMissing([], fwEmpn, fwEmpn2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fwEmpn);
          expect(expectedResult).toContain(fwEmpn2);
        });

        it('should accept null and undefined values', () => {
          const fwEmpn: IFwEmpn = { id: 123 };
          expectedResult = service.addFwEmpnToCollectionIfMissing([], null, fwEmpn, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fwEmpn);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
