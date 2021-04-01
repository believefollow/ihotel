import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAdhoc, Adhoc } from '../adhoc.model';

import { AdhocService } from './adhoc.service';

describe('Service Tests', () => {
  describe('Adhoc Service', () => {
    let service: AdhocService;
    let httpMock: HttpTestingController;
    let elemDefault: IAdhoc;
    let expectedResult: IAdhoc | IAdhoc[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AdhocService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 'AAAAAAA',
        remark: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find('ABC').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Adhoc', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Adhoc()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Adhoc', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            remark: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Adhoc', () => {
        const patchObject = Object.assign(
          {
            remark: 'BBBBBB',
          },
          new Adhoc()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Adhoc', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            remark: 'BBBBBB',
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

      it('should delete a Adhoc', () => {
        service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAdhocToCollectionIfMissing', () => {
        it('should add a Adhoc to an empty array', () => {
          const adhoc: IAdhoc = { id: 'ABC' };
          expectedResult = service.addAdhocToCollectionIfMissing([], adhoc);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(adhoc);
        });

        it('should not add a Adhoc to an array that contains it', () => {
          const adhoc: IAdhoc = { id: 'ABC' };
          const adhocCollection: IAdhoc[] = [
            {
              ...adhoc,
            },
            { id: 'CBA' },
          ];
          expectedResult = service.addAdhocToCollectionIfMissing(adhocCollection, adhoc);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Adhoc to an array that doesn't contain it", () => {
          const adhoc: IAdhoc = { id: 'ABC' };
          const adhocCollection: IAdhoc[] = [{ id: 'CBA' }];
          expectedResult = service.addAdhocToCollectionIfMissing(adhocCollection, adhoc);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(adhoc);
        });

        it('should add only unique Adhoc to an array', () => {
          const adhocArray: IAdhoc[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'Avon capac' }];
          const adhocCollection: IAdhoc[] = [{ id: 'ABC' }];
          expectedResult = service.addAdhocToCollectionIfMissing(adhocCollection, ...adhocArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const adhoc: IAdhoc = { id: 'ABC' };
          const adhoc2: IAdhoc = { id: 'CBA' };
          expectedResult = service.addAdhocToCollectionIfMissing([], adhoc, adhoc2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(adhoc);
          expect(expectedResult).toContain(adhoc2);
        });

        it('should accept null and undefined values', () => {
          const adhoc: IAdhoc = { id: 'ABC' };
          expectedResult = service.addAdhocToCollectionIfMissing([], null, adhoc, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(adhoc);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
