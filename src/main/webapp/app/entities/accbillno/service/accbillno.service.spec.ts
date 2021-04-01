import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAccbillno, Accbillno } from '../accbillno.model';

import { AccbillnoService } from './accbillno.service';

describe('Service Tests', () => {
  describe('Accbillno Service', () => {
    let service: AccbillnoService;
    let httpMock: HttpTestingController;
    let elemDefault: IAccbillno;
    let expectedResult: IAccbillno | IAccbillno[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AccbillnoService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        account: 'AAAAAAA',
        accbillno: 'AAAAAAA',
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

      it('should create a Accbillno', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Accbillno()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Accbillno', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            account: 'BBBBBB',
            accbillno: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Accbillno', () => {
        const patchObject = Object.assign(
          {
            accbillno: 'BBBBBB',
          },
          new Accbillno()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Accbillno', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            account: 'BBBBBB',
            accbillno: 'BBBBBB',
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

      it('should delete a Accbillno', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAccbillnoToCollectionIfMissing', () => {
        it('should add a Accbillno to an empty array', () => {
          const accbillno: IAccbillno = { id: 123 };
          expectedResult = service.addAccbillnoToCollectionIfMissing([], accbillno);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(accbillno);
        });

        it('should not add a Accbillno to an array that contains it', () => {
          const accbillno: IAccbillno = { id: 123 };
          const accbillnoCollection: IAccbillno[] = [
            {
              ...accbillno,
            },
            { id: 456 },
          ];
          expectedResult = service.addAccbillnoToCollectionIfMissing(accbillnoCollection, accbillno);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Accbillno to an array that doesn't contain it", () => {
          const accbillno: IAccbillno = { id: 123 };
          const accbillnoCollection: IAccbillno[] = [{ id: 456 }];
          expectedResult = service.addAccbillnoToCollectionIfMissing(accbillnoCollection, accbillno);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(accbillno);
        });

        it('should add only unique Accbillno to an array', () => {
          const accbillnoArray: IAccbillno[] = [{ id: 123 }, { id: 456 }, { id: 91855 }];
          const accbillnoCollection: IAccbillno[] = [{ id: 123 }];
          expectedResult = service.addAccbillnoToCollectionIfMissing(accbillnoCollection, ...accbillnoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const accbillno: IAccbillno = { id: 123 };
          const accbillno2: IAccbillno = { id: 456 };
          expectedResult = service.addAccbillnoToCollectionIfMissing([], accbillno, accbillno2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(accbillno);
          expect(expectedResult).toContain(accbillno2);
        });

        it('should accept null and undefined values', () => {
          const accbillno: IAccbillno = { id: 123 };
          expectedResult = service.addAccbillnoToCollectionIfMissing([], null, accbillno, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(accbillno);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
