import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAcc, Acc } from '../acc.model';

import { AccService } from './acc.service';

describe('Service Tests', () => {
  describe('Acc Service', () => {
    let service: AccService;
    let httpMock: HttpTestingController;
    let elemDefault: IAcc;
    let expectedResult: IAcc | IAcc[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AccService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        acc: 'AAAAAAA',
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

      it('should create a Acc', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Acc()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Acc', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            acc: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Acc', () => {
        const patchObject = Object.assign(
          {
            acc: 'BBBBBB',
          },
          new Acc()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Acc', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            acc: 'BBBBBB',
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

      it('should delete a Acc', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAccToCollectionIfMissing', () => {
        it('should add a Acc to an empty array', () => {
          const acc: IAcc = { id: 123 };
          expectedResult = service.addAccToCollectionIfMissing([], acc);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(acc);
        });

        it('should not add a Acc to an array that contains it', () => {
          const acc: IAcc = { id: 123 };
          const accCollection: IAcc[] = [
            {
              ...acc,
            },
            { id: 456 },
          ];
          expectedResult = service.addAccToCollectionIfMissing(accCollection, acc);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Acc to an array that doesn't contain it", () => {
          const acc: IAcc = { id: 123 };
          const accCollection: IAcc[] = [{ id: 456 }];
          expectedResult = service.addAccToCollectionIfMissing(accCollection, acc);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(acc);
        });

        it('should add only unique Acc to an array', () => {
          const accArray: IAcc[] = [{ id: 123 }, { id: 456 }, { id: 75304 }];
          const accCollection: IAcc[] = [{ id: 123 }];
          expectedResult = service.addAccToCollectionIfMissing(accCollection, ...accArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const acc: IAcc = { id: 123 };
          const acc2: IAcc = { id: 456 };
          expectedResult = service.addAccToCollectionIfMissing([], acc, acc2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(acc);
          expect(expectedResult).toContain(acc2);
        });

        it('should accept null and undefined values', () => {
          const acc: IAcc = { id: 123 };
          expectedResult = service.addAccToCollectionIfMissing([], null, acc, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(acc);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
