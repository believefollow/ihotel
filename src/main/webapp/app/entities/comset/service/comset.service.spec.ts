import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IComset, Comset } from '../comset.model';

import { ComsetService } from './comset.service';

describe('Service Tests', () => {
  describe('Comset Service', () => {
    let service: ComsetService;
    let httpMock: HttpTestingController;
    let elemDefault: IComset;
    let expectedResult: IComset | IComset[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ComsetService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        comNum: 'AAAAAAA',
        comBytes: 'AAAAAAA',
        comDatabit: 'AAAAAAA',
        comParitycheck: 'AAAAAAA',
        comStopbit: 'AAAAAAA',
        comFunction: 0,
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

      it('should create a Comset', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Comset()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Comset', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            comNum: 'BBBBBB',
            comBytes: 'BBBBBB',
            comDatabit: 'BBBBBB',
            comParitycheck: 'BBBBBB',
            comStopbit: 'BBBBBB',
            comFunction: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Comset', () => {
        const patchObject = Object.assign(
          {
            comNum: 'BBBBBB',
            comParitycheck: 'BBBBBB',
            comStopbit: 'BBBBBB',
            comFunction: 1,
          },
          new Comset()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Comset', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            comNum: 'BBBBBB',
            comBytes: 'BBBBBB',
            comDatabit: 'BBBBBB',
            comParitycheck: 'BBBBBB',
            comStopbit: 'BBBBBB',
            comFunction: 1,
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

      it('should delete a Comset', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addComsetToCollectionIfMissing', () => {
        it('should add a Comset to an empty array', () => {
          const comset: IComset = { id: 123 };
          expectedResult = service.addComsetToCollectionIfMissing([], comset);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(comset);
        });

        it('should not add a Comset to an array that contains it', () => {
          const comset: IComset = { id: 123 };
          const comsetCollection: IComset[] = [
            {
              ...comset,
            },
            { id: 456 },
          ];
          expectedResult = service.addComsetToCollectionIfMissing(comsetCollection, comset);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Comset to an array that doesn't contain it", () => {
          const comset: IComset = { id: 123 };
          const comsetCollection: IComset[] = [{ id: 456 }];
          expectedResult = service.addComsetToCollectionIfMissing(comsetCollection, comset);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(comset);
        });

        it('should add only unique Comset to an array', () => {
          const comsetArray: IComset[] = [{ id: 123 }, { id: 456 }, { id: 22357 }];
          const comsetCollection: IComset[] = [{ id: 123 }];
          expectedResult = service.addComsetToCollectionIfMissing(comsetCollection, ...comsetArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const comset: IComset = { id: 123 };
          const comset2: IComset = { id: 456 };
          expectedResult = service.addComsetToCollectionIfMissing([], comset, comset2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(comset);
          expect(expectedResult).toContain(comset2);
        });

        it('should accept null and undefined values', () => {
          const comset: IComset = { id: 123 };
          expectedResult = service.addComsetToCollectionIfMissing([], null, comset, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(comset);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
