import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDCk, DCk } from '../d-ck.model';

import { DCkService } from './d-ck.service';

describe('Service Tests', () => {
  describe('DCk Service', () => {
    let service: DCkService;
    let httpMock: HttpTestingController;
    let elemDefault: IDCk;
    let expectedResult: IDCk | IDCk[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DCkService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        depot: 'AAAAAAA',
        ckdate: currentDate,
        ckbillno: 'AAAAAAA',
        deptname: 'AAAAAAA',
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
        dbSign: 0,
        cktype: 'AAAAAAA',
        empn: 'AAAAAAA',
        lrdate: currentDate,
        kcid: 0,
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
            ckdate: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a DCk', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            ckdate: currentDate.format(DATE_TIME_FORMAT),
            lrdate: currentDate.format(DATE_TIME_FORMAT),
            f1sj: currentDate.format(DATE_TIME_FORMAT),
            f2sj: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ckdate: currentDate,
            lrdate: currentDate,
            f1sj: currentDate,
            f2sj: currentDate,
          },
          returnedFromService
        );

        service.create(new DCk()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DCk', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            depot: 'BBBBBB',
            ckdate: currentDate.format(DATE_TIME_FORMAT),
            ckbillno: 'BBBBBB',
            deptname: 'BBBBBB',
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
            dbSign: 1,
            cktype: 'BBBBBB',
            empn: 'BBBBBB',
            lrdate: currentDate.format(DATE_TIME_FORMAT),
            kcid: 1,
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
            ckdate: currentDate,
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

      it('should partial update a DCk', () => {
        const patchObject = Object.assign(
          {
            ckdate: currentDate.format(DATE_TIME_FORMAT),
            deptname: 'BBBBBB',
            jbr: 'BBBBBB',
            remark: 'BBBBBB',
            spbm: 'BBBBBB',
            memo: 'BBBBBB',
            flag: 1,
            cktype: 'BBBBBB',
            empn: 'BBBBBB',
            kcid: 1,
            f1: 'BBBBBB',
            f1empn: 'BBBBBB',
            f2empn: 'BBBBBB',
            f1sj: currentDate.format(DATE_TIME_FORMAT),
            f2sj: currentDate.format(DATE_TIME_FORMAT),
          },
          new DCk()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            ckdate: currentDate,
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

      it('should return a list of DCk', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            depot: 'BBBBBB',
            ckdate: currentDate.format(DATE_TIME_FORMAT),
            ckbillno: 'BBBBBB',
            deptname: 'BBBBBB',
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
            dbSign: 1,
            cktype: 'BBBBBB',
            empn: 'BBBBBB',
            lrdate: currentDate.format(DATE_TIME_FORMAT),
            kcid: 1,
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
            ckdate: currentDate,
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

      it('should delete a DCk', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDCkToCollectionIfMissing', () => {
        it('should add a DCk to an empty array', () => {
          const dCk: IDCk = { id: 123 };
          expectedResult = service.addDCkToCollectionIfMissing([], dCk);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dCk);
        });

        it('should not add a DCk to an array that contains it', () => {
          const dCk: IDCk = { id: 123 };
          const dCkCollection: IDCk[] = [
            {
              ...dCk,
            },
            { id: 456 },
          ];
          expectedResult = service.addDCkToCollectionIfMissing(dCkCollection, dCk);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DCk to an array that doesn't contain it", () => {
          const dCk: IDCk = { id: 123 };
          const dCkCollection: IDCk[] = [{ id: 456 }];
          expectedResult = service.addDCkToCollectionIfMissing(dCkCollection, dCk);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dCk);
        });

        it('should add only unique DCk to an array', () => {
          const dCkArray: IDCk[] = [{ id: 123 }, { id: 456 }, { id: 38739 }];
          const dCkCollection: IDCk[] = [{ id: 123 }];
          expectedResult = service.addDCkToCollectionIfMissing(dCkCollection, ...dCkArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dCk: IDCk = { id: 123 };
          const dCk2: IDCk = { id: 456 };
          expectedResult = service.addDCkToCollectionIfMissing([], dCk, dCk2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dCk);
          expect(expectedResult).toContain(dCk2);
        });

        it('should accept null and undefined values', () => {
          const dCk: IDCk = { id: 123 };
          expectedResult = service.addDCkToCollectionIfMissing([], null, dCk, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dCk);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
