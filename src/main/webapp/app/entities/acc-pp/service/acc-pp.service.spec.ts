import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAccPp, AccPp } from '../acc-pp.model';

import { AccPpService } from './acc-pp.service';

describe('Service Tests', () => {
  describe('AccPp Service', () => {
    let service: AccPpService;
    let httpMock: HttpTestingController;
    let elemDefault: IAccPp;
    let expectedResult: IAccPp | IAccPp[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AccPpService);
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

      it('should create a AccPp', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AccPp()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AccPp', () => {
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

      it('should partial update a AccPp', () => {
        const patchObject = Object.assign({}, new AccPp());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AccPp', () => {
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

      it('should delete a AccPp', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAccPpToCollectionIfMissing', () => {
        it('should add a AccPp to an empty array', () => {
          const accPp: IAccPp = { id: 123 };
          expectedResult = service.addAccPpToCollectionIfMissing([], accPp);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(accPp);
        });

        it('should not add a AccPp to an array that contains it', () => {
          const accPp: IAccPp = { id: 123 };
          const accPpCollection: IAccPp[] = [
            {
              ...accPp,
            },
            { id: 456 },
          ];
          expectedResult = service.addAccPpToCollectionIfMissing(accPpCollection, accPp);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a AccPp to an array that doesn't contain it", () => {
          const accPp: IAccPp = { id: 123 };
          const accPpCollection: IAccPp[] = [{ id: 456 }];
          expectedResult = service.addAccPpToCollectionIfMissing(accPpCollection, accPp);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(accPp);
        });

        it('should add only unique AccPp to an array', () => {
          const accPpArray: IAccPp[] = [{ id: 123 }, { id: 456 }, { id: 2897 }];
          const accPpCollection: IAccPp[] = [{ id: 123 }];
          expectedResult = service.addAccPpToCollectionIfMissing(accPpCollection, ...accPpArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const accPp: IAccPp = { id: 123 };
          const accPp2: IAccPp = { id: 456 };
          expectedResult = service.addAccPpToCollectionIfMissing([], accPp, accPp2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(accPp);
          expect(expectedResult).toContain(accPp2);
        });

        it('should accept null and undefined values', () => {
          const accPp: IAccPp = { id: 123 };
          expectedResult = service.addAccPpToCollectionIfMissing([], null, accPp, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(accPp);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
