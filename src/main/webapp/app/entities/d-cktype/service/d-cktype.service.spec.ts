import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDCktype, DCktype } from '../d-cktype.model';

import { DCktypeService } from './d-cktype.service';

describe('Service Tests', () => {
  describe('DCktype Service', () => {
    let service: DCktypeService;
    let httpMock: HttpTestingController;
    let elemDefault: IDCktype;
    let expectedResult: IDCktype | IDCktype[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DCktypeService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        cktype: 'AAAAAAA',
        memo: 'AAAAAAA',
        sign: 'AAAAAAA',
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

      it('should create a DCktype', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DCktype()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DCktype', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            cktype: 'BBBBBB',
            memo: 'BBBBBB',
            sign: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DCktype', () => {
        const patchObject = Object.assign(
          {
            sign: 'BBBBBB',
          },
          new DCktype()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DCktype', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            cktype: 'BBBBBB',
            memo: 'BBBBBB',
            sign: 'BBBBBB',
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

      it('should delete a DCktype', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDCktypeToCollectionIfMissing', () => {
        it('should add a DCktype to an empty array', () => {
          const dCktype: IDCktype = { id: 123 };
          expectedResult = service.addDCktypeToCollectionIfMissing([], dCktype);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dCktype);
        });

        it('should not add a DCktype to an array that contains it', () => {
          const dCktype: IDCktype = { id: 123 };
          const dCktypeCollection: IDCktype[] = [
            {
              ...dCktype,
            },
            { id: 456 },
          ];
          expectedResult = service.addDCktypeToCollectionIfMissing(dCktypeCollection, dCktype);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DCktype to an array that doesn't contain it", () => {
          const dCktype: IDCktype = { id: 123 };
          const dCktypeCollection: IDCktype[] = [{ id: 456 }];
          expectedResult = service.addDCktypeToCollectionIfMissing(dCktypeCollection, dCktype);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dCktype);
        });

        it('should add only unique DCktype to an array', () => {
          const dCktypeArray: IDCktype[] = [{ id: 123 }, { id: 456 }, { id: 8506 }];
          const dCktypeCollection: IDCktype[] = [{ id: 123 }];
          expectedResult = service.addDCktypeToCollectionIfMissing(dCktypeCollection, ...dCktypeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dCktype: IDCktype = { id: 123 };
          const dCktype2: IDCktype = { id: 456 };
          expectedResult = service.addDCktypeToCollectionIfMissing([], dCktype, dCktype2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dCktype);
          expect(expectedResult).toContain(dCktype2);
        });

        it('should accept null and undefined values', () => {
          const dCktype: IDCktype = { id: 123 };
          expectedResult = service.addDCktypeToCollectionIfMissing([], null, dCktype, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dCktype);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
