import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEe, Ee } from '../ee.model';

import { EeService } from './ee.service';

describe('Service Tests', () => {
  describe('Ee Service', () => {
    let service: EeService;
    let httpMock: HttpTestingController;
    let elemDefault: IEe;
    let expectedResult: IEe | IEe[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EeService);
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

      it('should create a Ee', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Ee()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Ee', () => {
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

      it('should partial update a Ee', () => {
        const patchObject = Object.assign({}, new Ee());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Ee', () => {
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

      it('should delete a Ee', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEeToCollectionIfMissing', () => {
        it('should add a Ee to an empty array', () => {
          const ee: IEe = { id: 123 };
          expectedResult = service.addEeToCollectionIfMissing([], ee);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ee);
        });

        it('should not add a Ee to an array that contains it', () => {
          const ee: IEe = { id: 123 };
          const eeCollection: IEe[] = [
            {
              ...ee,
            },
            { id: 456 },
          ];
          expectedResult = service.addEeToCollectionIfMissing(eeCollection, ee);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Ee to an array that doesn't contain it", () => {
          const ee: IEe = { id: 123 };
          const eeCollection: IEe[] = [{ id: 456 }];
          expectedResult = service.addEeToCollectionIfMissing(eeCollection, ee);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ee);
        });

        it('should add only unique Ee to an array', () => {
          const eeArray: IEe[] = [{ id: 123 }, { id: 456 }, { id: 21688 }];
          const eeCollection: IEe[] = [{ id: 123 }];
          expectedResult = service.addEeToCollectionIfMissing(eeCollection, ...eeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const ee: IEe = { id: 123 };
          const ee2: IEe = { id: 456 };
          expectedResult = service.addEeToCollectionIfMissing([], ee, ee2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ee);
          expect(expectedResult).toContain(ee2);
        });

        it('should accept null and undefined values', () => {
          const ee: IEe = { id: 123 };
          expectedResult = service.addEeToCollectionIfMissing([], null, ee, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ee);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
