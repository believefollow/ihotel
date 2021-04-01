import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICyRoomtype, CyRoomtype } from '../cy-roomtype.model';

import { CyRoomtypeService } from './cy-roomtype.service';

describe('Service Tests', () => {
  describe('CyRoomtype Service', () => {
    let service: CyRoomtypeService;
    let httpMock: HttpTestingController;
    let elemDefault: ICyRoomtype;
    let expectedResult: ICyRoomtype | ICyRoomtype[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CyRoomtypeService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        rtdm: 'AAAAAAA',
        minc: 0,
        servicerate: 0,
        printer: 'AAAAAAA',
        printnum: 0,
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

      it('should create a CyRoomtype', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CyRoomtype()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CyRoomtype', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            rtdm: 'BBBBBB',
            minc: 1,
            servicerate: 1,
            printer: 'BBBBBB',
            printnum: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CyRoomtype', () => {
        const patchObject = Object.assign(
          {
            minc: 1,
            printer: 'BBBBBB',
            printnum: 1,
          },
          new CyRoomtype()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CyRoomtype', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            rtdm: 'BBBBBB',
            minc: 1,
            servicerate: 1,
            printer: 'BBBBBB',
            printnum: 1,
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

      it('should delete a CyRoomtype', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCyRoomtypeToCollectionIfMissing', () => {
        it('should add a CyRoomtype to an empty array', () => {
          const cyRoomtype: ICyRoomtype = { id: 123 };
          expectedResult = service.addCyRoomtypeToCollectionIfMissing([], cyRoomtype);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cyRoomtype);
        });

        it('should not add a CyRoomtype to an array that contains it', () => {
          const cyRoomtype: ICyRoomtype = { id: 123 };
          const cyRoomtypeCollection: ICyRoomtype[] = [
            {
              ...cyRoomtype,
            },
            { id: 456 },
          ];
          expectedResult = service.addCyRoomtypeToCollectionIfMissing(cyRoomtypeCollection, cyRoomtype);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CyRoomtype to an array that doesn't contain it", () => {
          const cyRoomtype: ICyRoomtype = { id: 123 };
          const cyRoomtypeCollection: ICyRoomtype[] = [{ id: 456 }];
          expectedResult = service.addCyRoomtypeToCollectionIfMissing(cyRoomtypeCollection, cyRoomtype);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cyRoomtype);
        });

        it('should add only unique CyRoomtype to an array', () => {
          const cyRoomtypeArray: ICyRoomtype[] = [{ id: 123 }, { id: 456 }, { id: 12389 }];
          const cyRoomtypeCollection: ICyRoomtype[] = [{ id: 123 }];
          expectedResult = service.addCyRoomtypeToCollectionIfMissing(cyRoomtypeCollection, ...cyRoomtypeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const cyRoomtype: ICyRoomtype = { id: 123 };
          const cyRoomtype2: ICyRoomtype = { id: 456 };
          expectedResult = service.addCyRoomtypeToCollectionIfMissing([], cyRoomtype, cyRoomtype2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cyRoomtype);
          expect(expectedResult).toContain(cyRoomtype2);
        });

        it('should accept null and undefined values', () => {
          const cyRoomtype: ICyRoomtype = { id: 123 };
          expectedResult = service.addCyRoomtypeToCollectionIfMissing([], null, cyRoomtype, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cyRoomtype);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
