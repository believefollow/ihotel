import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICyCptype, CyCptype } from '../cy-cptype.model';

import { CyCptypeService } from './cy-cptype.service';

describe('Service Tests', () => {
  describe('CyCptype Service', () => {
    let service: CyCptypeService;
    let httpMock: HttpTestingController;
    let elemDefault: ICyCptype;
    let expectedResult: ICyCptype | ICyCptype[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CyCptypeService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        cptdm: 'AAAAAAA',
        cptname: 'AAAAAAA',
        printsign: false,
        printer: 'AAAAAAA',
        printnum: 0,
        printcut: 0,
        syssign: false,
        typesign: 'AAAAAAA',
        qy: 'AAAAAAA',
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

      it('should create a CyCptype', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CyCptype()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CyCptype', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            cptdm: 'BBBBBB',
            cptname: 'BBBBBB',
            printsign: true,
            printer: 'BBBBBB',
            printnum: 1,
            printcut: 1,
            syssign: true,
            typesign: 'BBBBBB',
            qy: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CyCptype', () => {
        const patchObject = Object.assign(
          {
            cptdm: 'BBBBBB',
            printsign: true,
            printcut: 1,
            typesign: 'BBBBBB',
          },
          new CyCptype()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CyCptype', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            cptdm: 'BBBBBB',
            cptname: 'BBBBBB',
            printsign: true,
            printer: 'BBBBBB',
            printnum: 1,
            printcut: 1,
            syssign: true,
            typesign: 'BBBBBB',
            qy: 'BBBBBB',
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

      it('should delete a CyCptype', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCyCptypeToCollectionIfMissing', () => {
        it('should add a CyCptype to an empty array', () => {
          const cyCptype: ICyCptype = { id: 123 };
          expectedResult = service.addCyCptypeToCollectionIfMissing([], cyCptype);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cyCptype);
        });

        it('should not add a CyCptype to an array that contains it', () => {
          const cyCptype: ICyCptype = { id: 123 };
          const cyCptypeCollection: ICyCptype[] = [
            {
              ...cyCptype,
            },
            { id: 456 },
          ];
          expectedResult = service.addCyCptypeToCollectionIfMissing(cyCptypeCollection, cyCptype);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CyCptype to an array that doesn't contain it", () => {
          const cyCptype: ICyCptype = { id: 123 };
          const cyCptypeCollection: ICyCptype[] = [{ id: 456 }];
          expectedResult = service.addCyCptypeToCollectionIfMissing(cyCptypeCollection, cyCptype);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cyCptype);
        });

        it('should add only unique CyCptype to an array', () => {
          const cyCptypeArray: ICyCptype[] = [{ id: 123 }, { id: 456 }, { id: 61564 }];
          const cyCptypeCollection: ICyCptype[] = [{ id: 123 }];
          expectedResult = service.addCyCptypeToCollectionIfMissing(cyCptypeCollection, ...cyCptypeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const cyCptype: ICyCptype = { id: 123 };
          const cyCptype2: ICyCptype = { id: 456 };
          expectedResult = service.addCyCptypeToCollectionIfMissing([], cyCptype, cyCptype2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cyCptype);
          expect(expectedResult).toContain(cyCptype2);
        });

        it('should accept null and undefined values', () => {
          const cyCptype: ICyCptype = { id: 123 };
          expectedResult = service.addCyCptypeToCollectionIfMissing([], null, cyCptype, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cyCptype);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
