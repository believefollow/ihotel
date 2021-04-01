import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDRk, DRk } from '../d-rk.model';

import { DRkService } from './d-rk.service';

describe('Service Tests', () => {
  describe('DRk Service', () => {
    let service: DRkService;
    let httpMock: HttpTestingController;
    let elemDefault: IDRk;
    let expectedResult: IDRk | IDRk[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DRkService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        rkdate: currentDate,
        depot: 'AAAAAAA',
        rklx: 'AAAAAAA',
        rkbillno: 'AAAAAAA',
        company: 0,
        deptname: 'AAAAAAA',
        jbr: 'AAAAAAA',
        remark: 'AAAAAAA',
        empn: 'AAAAAAA',
        lrdate: currentDate,
        spbm: 'AAAAAAA',
        spmc: 'AAAAAAA',
        ggxh: 'AAAAAAA',
        dw: 'AAAAAAA',
        price: 0,
        sl: 0,
        je: 0,
        sign: 0,
        memo: 'AAAAAAA',
        flag: 0,
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
            rkdate: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a DRk', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            rkdate: currentDate.format(DATE_TIME_FORMAT),
            lrdate: currentDate.format(DATE_TIME_FORMAT),
            f1sj: currentDate.format(DATE_TIME_FORMAT),
            f2sj: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            rkdate: currentDate,
            lrdate: currentDate,
            f1sj: currentDate,
            f2sj: currentDate,
          },
          returnedFromService
        );

        service.create(new DRk()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DRk', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            rkdate: currentDate.format(DATE_TIME_FORMAT),
            depot: 'BBBBBB',
            rklx: 'BBBBBB',
            rkbillno: 'BBBBBB',
            company: 1,
            deptname: 'BBBBBB',
            jbr: 'BBBBBB',
            remark: 'BBBBBB',
            empn: 'BBBBBB',
            lrdate: currentDate.format(DATE_TIME_FORMAT),
            spbm: 'BBBBBB',
            spmc: 'BBBBBB',
            ggxh: 'BBBBBB',
            dw: 'BBBBBB',
            price: 1,
            sl: 1,
            je: 1,
            sign: 1,
            memo: 'BBBBBB',
            flag: 1,
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
            rkdate: currentDate,
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

      it('should partial update a DRk', () => {
        const patchObject = Object.assign(
          {
            rkbillno: 'BBBBBB',
            deptname: 'BBBBBB',
            jbr: 'BBBBBB',
            remark: 'BBBBBB',
            lrdate: currentDate.format(DATE_TIME_FORMAT),
            dw: 'BBBBBB',
            memo: 'BBBBBB',
            f1: 'BBBBBB',
            f2: 'BBBBBB',
            f2empn: 'BBBBBB',
          },
          new DRk()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            rkdate: currentDate,
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

      it('should return a list of DRk', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            rkdate: currentDate.format(DATE_TIME_FORMAT),
            depot: 'BBBBBB',
            rklx: 'BBBBBB',
            rkbillno: 'BBBBBB',
            company: 1,
            deptname: 'BBBBBB',
            jbr: 'BBBBBB',
            remark: 'BBBBBB',
            empn: 'BBBBBB',
            lrdate: currentDate.format(DATE_TIME_FORMAT),
            spbm: 'BBBBBB',
            spmc: 'BBBBBB',
            ggxh: 'BBBBBB',
            dw: 'BBBBBB',
            price: 1,
            sl: 1,
            je: 1,
            sign: 1,
            memo: 'BBBBBB',
            flag: 1,
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
            rkdate: currentDate,
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

      it('should delete a DRk', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDRkToCollectionIfMissing', () => {
        it('should add a DRk to an empty array', () => {
          const dRk: IDRk = { id: 123 };
          expectedResult = service.addDRkToCollectionIfMissing([], dRk);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dRk);
        });

        it('should not add a DRk to an array that contains it', () => {
          const dRk: IDRk = { id: 123 };
          const dRkCollection: IDRk[] = [
            {
              ...dRk,
            },
            { id: 456 },
          ];
          expectedResult = service.addDRkToCollectionIfMissing(dRkCollection, dRk);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DRk to an array that doesn't contain it", () => {
          const dRk: IDRk = { id: 123 };
          const dRkCollection: IDRk[] = [{ id: 456 }];
          expectedResult = service.addDRkToCollectionIfMissing(dRkCollection, dRk);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dRk);
        });

        it('should add only unique DRk to an array', () => {
          const dRkArray: IDRk[] = [{ id: 123 }, { id: 456 }, { id: 69986 }];
          const dRkCollection: IDRk[] = [{ id: 123 }];
          expectedResult = service.addDRkToCollectionIfMissing(dRkCollection, ...dRkArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dRk: IDRk = { id: 123 };
          const dRk2: IDRk = { id: 456 };
          expectedResult = service.addDRkToCollectionIfMissing([], dRk, dRk2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dRk);
          expect(expectedResult).toContain(dRk2);
        });

        it('should accept null and undefined values', () => {
          const dRk: IDRk = { id: 123 };
          expectedResult = service.addDRkToCollectionIfMissing([], null, dRk, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dRk);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
