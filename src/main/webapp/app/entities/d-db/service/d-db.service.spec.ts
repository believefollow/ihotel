import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDDb, DDb } from '../d-db.model';

import { DDbService } from './d-db.service';

describe('Service Tests', () => {
  describe('DDb Service', () => {
    let service: DDbService;
    let httpMock: HttpTestingController;
    let elemDefault: IDDb;
    let expectedResult: IDDb | IDDb[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DDbService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        dbdate: currentDate,
        dbbillno: 'AAAAAAA',
        rdepot: 'AAAAAAA',
        cdepot: 'AAAAAAA',
        jbr: 'AAAAAAA',
        remark: 'AAAAAAA',
        spbm: 'AAAAAAA',
        spmc: 'AAAAAAA',
        unit: 'AAAAAAA',
        price: 0,
        sl: 0,
        je: 0,
        memo: 'AAAAAAA',
        flag: 0,
        kcid: 0,
        empn: 'AAAAAAA',
        lrdate: currentDate,
        ckbillno: 'AAAAAAA',
        f1: 'AAAAAAA',
        f2: 'AAAAAAA',
        f1empn: 'AAAAAAA',
        f2empn: 'AAAAAAA',
        f1sj: currentDate,
        f2sj: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dbdate: currentDate.format(DATE_TIME_FORMAT),
            lrdate: currentDate.format(DATE_TIME_FORMAT),
            f1sj: currentDate.format(DATE_TIME_FORMAT),
            f2sj: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DDb', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dbdate: currentDate.format(DATE_TIME_FORMAT),
            lrdate: currentDate.format(DATE_TIME_FORMAT),
            f1sj: currentDate.format(DATE_TIME_FORMAT),
            f2sj: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dbdate: currentDate,
            lrdate: currentDate,
            f1sj: currentDate,
            f2sj: currentDate,
          },
          returnedFromService
        );

        service.create(new DDb()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DDb', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            dbdate: currentDate.format(DATE_TIME_FORMAT),
            dbbillno: 'BBBBBB',
            rdepot: 'BBBBBB',
            cdepot: 'BBBBBB',
            jbr: 'BBBBBB',
            remark: 'BBBBBB',
            spbm: 'BBBBBB',
            spmc: 'BBBBBB',
            unit: 'BBBBBB',
            price: 1,
            sl: 1,
            je: 1,
            memo: 'BBBBBB',
            flag: 1,
            kcid: 1,
            empn: 'BBBBBB',
            lrdate: currentDate.format(DATE_TIME_FORMAT),
            ckbillno: 'BBBBBB',
            f1: 'BBBBBB',
            f2: 'BBBBBB',
            f1empn: 'BBBBBB',
            f2empn: 'BBBBBB',
            f1sj: currentDate.format(DATE_TIME_FORMAT),
            f2sj: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dbdate: currentDate,
            lrdate: currentDate,
            f1sj: currentDate,
            f2sj: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DDb', () => {
        const patchObject = Object.assign(
          {
            dbdate: currentDate.format(DATE_TIME_FORMAT),
            rdepot: 'BBBBBB',
            jbr: 'BBBBBB',
            unit: 'BBBBBB',
            je: 1,
            memo: 'BBBBBB',
            flag: 1,
            empn: 'BBBBBB',
            f2: 'BBBBBB',
            f1empn: 'BBBBBB',
            f1sj: currentDate.format(DATE_TIME_FORMAT),
            f2sj: currentDate.format(DATE_TIME_FORMAT),
          },
          new DDb()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dbdate: currentDate,
            lrdate: currentDate,
            f1sj: currentDate,
            f2sj: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DDb', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            dbdate: currentDate.format(DATE_TIME_FORMAT),
            dbbillno: 'BBBBBB',
            rdepot: 'BBBBBB',
            cdepot: 'BBBBBB',
            jbr: 'BBBBBB',
            remark: 'BBBBBB',
            spbm: 'BBBBBB',
            spmc: 'BBBBBB',
            unit: 'BBBBBB',
            price: 1,
            sl: 1,
            je: 1,
            memo: 'BBBBBB',
            flag: 1,
            kcid: 1,
            empn: 'BBBBBB',
            lrdate: currentDate.format(DATE_TIME_FORMAT),
            ckbillno: 'BBBBBB',
            f1: 'BBBBBB',
            f2: 'BBBBBB',
            f1empn: 'BBBBBB',
            f2empn: 'BBBBBB',
            f1sj: currentDate.format(DATE_TIME_FORMAT),
            f2sj: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dbdate: currentDate,
            lrdate: currentDate,
            f1sj: currentDate,
            f2sj: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DDb', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDDbToCollectionIfMissing', () => {
        it('should add a DDb to an empty array', () => {
          const dDb: IDDb = { id: 123 };
          expectedResult = service.addDDbToCollectionIfMissing([], dDb);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dDb);
        });

        it('should not add a DDb to an array that contains it', () => {
          const dDb: IDDb = { id: 123 };
          const dDbCollection: IDDb[] = [
            {
              ...dDb,
            },
            { id: 456 },
          ];
          expectedResult = service.addDDbToCollectionIfMissing(dDbCollection, dDb);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DDb to an array that doesn't contain it", () => {
          const dDb: IDDb = { id: 123 };
          const dDbCollection: IDDb[] = [{ id: 456 }];
          expectedResult = service.addDDbToCollectionIfMissing(dDbCollection, dDb);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dDb);
        });

        it('should add only unique DDb to an array', () => {
          const dDbArray: IDDb[] = [{ id: 123 }, { id: 456 }, { id: 85391 }];
          const dDbCollection: IDDb[] = [{ id: 123 }];
          expectedResult = service.addDDbToCollectionIfMissing(dDbCollection, ...dDbArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dDb: IDDb = { id: 123 };
          const dDb2: IDDb = { id: 456 };
          expectedResult = service.addDDbToCollectionIfMissing([], dDb, dDb2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dDb);
          expect(expectedResult).toContain(dDb2);
        });

        it('should accept null and undefined values', () => {
          const dDb: IDDb = { id: 123 };
          expectedResult = service.addDDbToCollectionIfMissing([], null, dDb, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dDb);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
