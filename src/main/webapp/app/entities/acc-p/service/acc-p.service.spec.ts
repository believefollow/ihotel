import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAccP, AccP } from '../acc-p.model';

import { AccPService } from './acc-p.service';

describe('Service Tests', () => {
  describe('AccP Service', () => {
    let service: AccPService;
    let httpMock: HttpTestingController;
    let elemDefault: IAccP;
    let expectedResult: IAccP | IAccP[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AccPService);
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

      it('should create a AccP', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AccP()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AccP', () => {
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

      it('should partial update a AccP', () => {
        const patchObject = Object.assign(
          {
            acc: 'BBBBBB',
          },
          new AccP()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AccP', () => {
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

      it('should delete a AccP', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAccPToCollectionIfMissing', () => {
        it('should add a AccP to an empty array', () => {
          const accP: IAccP = { id: 123 };
          expectedResult = service.addAccPToCollectionIfMissing([], accP);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(accP);
        });

        it('should not add a AccP to an array that contains it', () => {
          const accP: IAccP = { id: 123 };
          const accPCollection: IAccP[] = [
            {
              ...accP,
            },
            { id: 456 },
          ];
          expectedResult = service.addAccPToCollectionIfMissing(accPCollection, accP);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a AccP to an array that doesn't contain it", () => {
          const accP: IAccP = { id: 123 };
          const accPCollection: IAccP[] = [{ id: 456 }];
          expectedResult = service.addAccPToCollectionIfMissing(accPCollection, accP);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(accP);
        });

        it('should add only unique AccP to an array', () => {
          const accPArray: IAccP[] = [{ id: 123 }, { id: 456 }, { id: 90742 }];
          const accPCollection: IAccP[] = [{ id: 123 }];
          expectedResult = service.addAccPToCollectionIfMissing(accPCollection, ...accPArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const accP: IAccP = { id: 123 };
          const accP2: IAccP = { id: 456 };
          expectedResult = service.addAccPToCollectionIfMissing([], accP, accP2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(accP);
          expect(expectedResult).toContain(accP2);
        });

        it('should accept null and undefined values', () => {
          const accP: IAccP = { id: 123 };
          expectedResult = service.addAccPToCollectionIfMissing([], null, accP, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(accP);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
