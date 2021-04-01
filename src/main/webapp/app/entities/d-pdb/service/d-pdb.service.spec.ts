import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDPdb, DPdb } from '../d-pdb.model';

import { DPdbService } from './d-pdb.service';

describe('Service Tests', () => {
  describe('DPdb Service', () => {
    let service: DPdbService;
    let httpMock: HttpTestingController;
    let elemDefault: IDPdb;
    let expectedResult: IDPdb | IDPdb[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DPdbService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        begindate: currentDate,
        enddate: currentDate,
        bm: 'AAAAAAA',
        spmc: 'AAAAAAA',
        qcsl: 0,
        rksl: 0,
        xssl: 0,
        dbsl: 0,
        qtck: 0,
        jcsl: 0,
        swsl: 0,
        pyk: 0,
        memo: 'AAAAAAA',
        depot: 'AAAAAAA',
        rkje: 0,
        xsje: 0,
        dbje: 0,
        jcje: 0,
        dp: 'AAAAAAA',
        qcje: 0,
        swje: 0,
        qtje: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            begindate: currentDate.format(DATE_TIME_FORMAT),
            enddate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DPdb', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            begindate: currentDate.format(DATE_TIME_FORMAT),
            enddate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            begindate: currentDate,
            enddate: currentDate,
          },
          returnedFromService
        );

        service.create(new DPdb()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DPdb', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            begindate: currentDate.format(DATE_TIME_FORMAT),
            enddate: currentDate.format(DATE_TIME_FORMAT),
            bm: 'BBBBBB',
            spmc: 'BBBBBB',
            qcsl: 1,
            rksl: 1,
            xssl: 1,
            dbsl: 1,
            qtck: 1,
            jcsl: 1,
            swsl: 1,
            pyk: 1,
            memo: 'BBBBBB',
            depot: 'BBBBBB',
            rkje: 1,
            xsje: 1,
            dbje: 1,
            jcje: 1,
            dp: 'BBBBBB',
            qcje: 1,
            swje: 1,
            qtje: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            begindate: currentDate,
            enddate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DPdb', () => {
        const patchObject = Object.assign(
          {
            begindate: currentDate.format(DATE_TIME_FORMAT),
            enddate: currentDate.format(DATE_TIME_FORMAT),
            bm: 'BBBBBB',
            spmc: 'BBBBBB',
            qcsl: 1,
            rksl: 1,
            pyk: 1,
            depot: 'BBBBBB',
            xsje: 1,
            dbje: 1,
            swje: 1,
          },
          new DPdb()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            begindate: currentDate,
            enddate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DPdb', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            begindate: currentDate.format(DATE_TIME_FORMAT),
            enddate: currentDate.format(DATE_TIME_FORMAT),
            bm: 'BBBBBB',
            spmc: 'BBBBBB',
            qcsl: 1,
            rksl: 1,
            xssl: 1,
            dbsl: 1,
            qtck: 1,
            jcsl: 1,
            swsl: 1,
            pyk: 1,
            memo: 'BBBBBB',
            depot: 'BBBBBB',
            rkje: 1,
            xsje: 1,
            dbje: 1,
            jcje: 1,
            dp: 'BBBBBB',
            qcje: 1,
            swje: 1,
            qtje: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            begindate: currentDate,
            enddate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DPdb', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDPdbToCollectionIfMissing', () => {
        it('should add a DPdb to an empty array', () => {
          const dPdb: IDPdb = { id: 123 };
          expectedResult = service.addDPdbToCollectionIfMissing([], dPdb);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dPdb);
        });

        it('should not add a DPdb to an array that contains it', () => {
          const dPdb: IDPdb = { id: 123 };
          const dPdbCollection: IDPdb[] = [
            {
              ...dPdb,
            },
            { id: 456 },
          ];
          expectedResult = service.addDPdbToCollectionIfMissing(dPdbCollection, dPdb);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DPdb to an array that doesn't contain it", () => {
          const dPdb: IDPdb = { id: 123 };
          const dPdbCollection: IDPdb[] = [{ id: 456 }];
          expectedResult = service.addDPdbToCollectionIfMissing(dPdbCollection, dPdb);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dPdb);
        });

        it('should add only unique DPdb to an array', () => {
          const dPdbArray: IDPdb[] = [{ id: 123 }, { id: 456 }, { id: 21514 }];
          const dPdbCollection: IDPdb[] = [{ id: 123 }];
          expectedResult = service.addDPdbToCollectionIfMissing(dPdbCollection, ...dPdbArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dPdb: IDPdb = { id: 123 };
          const dPdb2: IDPdb = { id: 456 };
          expectedResult = service.addDPdbToCollectionIfMissing([], dPdb, dPdb2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dPdb);
          expect(expectedResult).toContain(dPdb2);
        });

        it('should accept null and undefined values', () => {
          const dPdb: IDPdb = { id: 123 };
          expectedResult = service.addDPdbToCollectionIfMissing([], null, dPdb, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dPdb);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
