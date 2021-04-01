import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDKc, DKc } from '../d-kc.model';

import { DKcService } from './d-kc.service';

describe('Service Tests', () => {
  describe('DKc Service', () => {
    let service: DKcService;
    let httpMock: HttpTestingController;
    let elemDefault: IDKc;
    let expectedResult: IDKc | IDKc[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DKcService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        depot: 'AAAAAAA',
        spbm: 'AAAAAAA',
        spmc: 'AAAAAAA',
        xh: 'AAAAAAA',
        dw: 'AAAAAAA',
        price: 0,
        sl: 0,
        je: 0,
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

      it('should create a DKc', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DKc()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DKc', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            depot: 'BBBBBB',
            spbm: 'BBBBBB',
            spmc: 'BBBBBB',
            xh: 'BBBBBB',
            dw: 'BBBBBB',
            price: 1,
            sl: 1,
            je: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DKc', () => {
        const patchObject = Object.assign(
          {
            price: 1,
            sl: 1,
            je: 1,
          },
          new DKc()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DKc', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            depot: 'BBBBBB',
            spbm: 'BBBBBB',
            spmc: 'BBBBBB',
            xh: 'BBBBBB',
            dw: 'BBBBBB',
            price: 1,
            sl: 1,
            je: 1,
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

      it('should delete a DKc', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDKcToCollectionIfMissing', () => {
        it('should add a DKc to an empty array', () => {
          const dKc: IDKc = { id: 123 };
          expectedResult = service.addDKcToCollectionIfMissing([], dKc);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dKc);
        });

        it('should not add a DKc to an array that contains it', () => {
          const dKc: IDKc = { id: 123 };
          const dKcCollection: IDKc[] = [
            {
              ...dKc,
            },
            { id: 456 },
          ];
          expectedResult = service.addDKcToCollectionIfMissing(dKcCollection, dKc);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DKc to an array that doesn't contain it", () => {
          const dKc: IDKc = { id: 123 };
          const dKcCollection: IDKc[] = [{ id: 456 }];
          expectedResult = service.addDKcToCollectionIfMissing(dKcCollection, dKc);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dKc);
        });

        it('should add only unique DKc to an array', () => {
          const dKcArray: IDKc[] = [{ id: 123 }, { id: 456 }, { id: 21925 }];
          const dKcCollection: IDKc[] = [{ id: 123 }];
          expectedResult = service.addDKcToCollectionIfMissing(dKcCollection, ...dKcArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dKc: IDKc = { id: 123 };
          const dKc2: IDKc = { id: 456 };
          expectedResult = service.addDKcToCollectionIfMissing([], dKc, dKc2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dKc);
          expect(expectedResult).toContain(dKc2);
        });

        it('should accept null and undefined values', () => {
          const dKc: IDKc = { id: 123 };
          expectedResult = service.addDKcToCollectionIfMissing([], null, dKc, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dKc);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
