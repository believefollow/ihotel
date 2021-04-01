import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICheckin, Checkin } from '../checkin.model';

import { CheckinService } from './checkin.service';

describe('Service Tests', () => {
  describe('Checkin Service', () => {
    let service: CheckinService;
    let httpMock: HttpTestingController;
    let elemDefault: ICheckin;
    let expectedResult: ICheckin | ICheckin[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CheckinService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        bkid: 0,
        guestId: 0,
        account: 'AAAAAAA',
        hoteltime: currentDate,
        indatetime: currentDate,
        residefate: 0,
        gotime: currentDate,
        empn: 'AAAAAAA',
        roomn: 'AAAAAAA',
        uname: 'AAAAAAA',
        rentp: 'AAAAAAA',
        protocolrent: 0,
        remark: 'AAAAAAA',
        phonen: 'AAAAAAA',
        empn2: 'AAAAAAA',
        adhoc: 'AAAAAAA',
        auditflag: 0,
        groupn: 'AAAAAAA',
        memo: 'AAAAAAA',
        lfSign: 'AAAAAAA',
        keynum: 'AAAAAAA',
        hykh: 'AAAAAAA',
        bm: 'AAAAAAA',
        flag: 0,
        jxtime: currentDate,
        jxflag: 0,
        checkf: 0,
        guestname: 'AAAAAAA',
        fgf: 0,
        fgxx: 'AAAAAAA',
        hourSign: 0,
        xsy: 'AAAAAAA',
        rzsign: 0,
        jf: 0,
        gname: 'AAAAAAA',
        zcsign: 0,
        cqsl: 0,
        sfjf: 0,
        ywly: 'AAAAAAA',
        fk: 'AAAAAAA',
        fkrq: currentDate,
        bc: 'AAAAAAA',
        jxremark: 'AAAAAAA',
        txid: 0,
        cfr: 'AAAAAAA',
        fjbm: 'AAAAAAA',
        djlx: 'AAAAAAA',
        wlddh: 'AAAAAAA',
        fksl: 0,
        dqtx: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            indatetime: currentDate.format(DATE_TIME_FORMAT),
            gotime: currentDate.format(DATE_TIME_FORMAT),
            jxtime: currentDate.format(DATE_TIME_FORMAT),
            fkrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Checkin', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            indatetime: currentDate.format(DATE_TIME_FORMAT),
            gotime: currentDate.format(DATE_TIME_FORMAT),
            jxtime: currentDate.format(DATE_TIME_FORMAT),
            fkrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            indatetime: currentDate,
            gotime: currentDate,
            jxtime: currentDate,
            fkrq: currentDate,
          },
          returnedFromService
        );

        service.create(new Checkin()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Checkin', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            bkid: 1,
            guestId: 1,
            account: 'BBBBBB',
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            indatetime: currentDate.format(DATE_TIME_FORMAT),
            residefate: 1,
            gotime: currentDate.format(DATE_TIME_FORMAT),
            empn: 'BBBBBB',
            roomn: 'BBBBBB',
            uname: 'BBBBBB',
            rentp: 'BBBBBB',
            protocolrent: 1,
            remark: 'BBBBBB',
            phonen: 'BBBBBB',
            empn2: 'BBBBBB',
            adhoc: 'BBBBBB',
            auditflag: 1,
            groupn: 'BBBBBB',
            memo: 'BBBBBB',
            lfSign: 'BBBBBB',
            keynum: 'BBBBBB',
            hykh: 'BBBBBB',
            bm: 'BBBBBB',
            flag: 1,
            jxtime: currentDate.format(DATE_TIME_FORMAT),
            jxflag: 1,
            checkf: 1,
            guestname: 'BBBBBB',
            fgf: 1,
            fgxx: 'BBBBBB',
            hourSign: 1,
            xsy: 'BBBBBB',
            rzsign: 1,
            jf: 1,
            gname: 'BBBBBB',
            zcsign: 1,
            cqsl: 1,
            sfjf: 1,
            ywly: 'BBBBBB',
            fk: 'BBBBBB',
            fkrq: currentDate.format(DATE_TIME_FORMAT),
            bc: 'BBBBBB',
            jxremark: 'BBBBBB',
            txid: 1,
            cfr: 'BBBBBB',
            fjbm: 'BBBBBB',
            djlx: 'BBBBBB',
            wlddh: 'BBBBBB',
            fksl: 1,
            dqtx: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            indatetime: currentDate,
            gotime: currentDate,
            jxtime: currentDate,
            fkrq: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Checkin', () => {
        const patchObject = Object.assign(
          {
            bkid: 1,
            guestId: 1,
            account: 'BBBBBB',
            empn: 'BBBBBB',
            roomn: 'BBBBBB',
            rentp: 'BBBBBB',
            protocolrent: 1,
            adhoc: 'BBBBBB',
            lfSign: 'BBBBBB',
            bm: 'BBBBBB',
            flag: 1,
            jxtime: currentDate.format(DATE_TIME_FORMAT),
            hourSign: 1,
            rzsign: 1,
            jf: 1,
            cqsl: 1,
            ywly: 'BBBBBB',
            fkrq: currentDate.format(DATE_TIME_FORMAT),
            bc: 'BBBBBB',
            jxremark: 'BBBBBB',
            wlddh: 'BBBBBB',
          },
          new Checkin()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            indatetime: currentDate,
            gotime: currentDate,
            jxtime: currentDate,
            fkrq: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Checkin', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            bkid: 1,
            guestId: 1,
            account: 'BBBBBB',
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            indatetime: currentDate.format(DATE_TIME_FORMAT),
            residefate: 1,
            gotime: currentDate.format(DATE_TIME_FORMAT),
            empn: 'BBBBBB',
            roomn: 'BBBBBB',
            uname: 'BBBBBB',
            rentp: 'BBBBBB',
            protocolrent: 1,
            remark: 'BBBBBB',
            phonen: 'BBBBBB',
            empn2: 'BBBBBB',
            adhoc: 'BBBBBB',
            auditflag: 1,
            groupn: 'BBBBBB',
            memo: 'BBBBBB',
            lfSign: 'BBBBBB',
            keynum: 'BBBBBB',
            hykh: 'BBBBBB',
            bm: 'BBBBBB',
            flag: 1,
            jxtime: currentDate.format(DATE_TIME_FORMAT),
            jxflag: 1,
            checkf: 1,
            guestname: 'BBBBBB',
            fgf: 1,
            fgxx: 'BBBBBB',
            hourSign: 1,
            xsy: 'BBBBBB',
            rzsign: 1,
            jf: 1,
            gname: 'BBBBBB',
            zcsign: 1,
            cqsl: 1,
            sfjf: 1,
            ywly: 'BBBBBB',
            fk: 'BBBBBB',
            fkrq: currentDate.format(DATE_TIME_FORMAT),
            bc: 'BBBBBB',
            jxremark: 'BBBBBB',
            txid: 1,
            cfr: 'BBBBBB',
            fjbm: 'BBBBBB',
            djlx: 'BBBBBB',
            wlddh: 'BBBBBB',
            fksl: 1,
            dqtx: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            indatetime: currentDate,
            gotime: currentDate,
            jxtime: currentDate,
            fkrq: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Checkin', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCheckinToCollectionIfMissing', () => {
        it('should add a Checkin to an empty array', () => {
          const checkin: ICheckin = { id: 123 };
          expectedResult = service.addCheckinToCollectionIfMissing([], checkin);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(checkin);
        });

        it('should not add a Checkin to an array that contains it', () => {
          const checkin: ICheckin = { id: 123 };
          const checkinCollection: ICheckin[] = [
            {
              ...checkin,
            },
            { id: 456 },
          ];
          expectedResult = service.addCheckinToCollectionIfMissing(checkinCollection, checkin);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Checkin to an array that doesn't contain it", () => {
          const checkin: ICheckin = { id: 123 };
          const checkinCollection: ICheckin[] = [{ id: 456 }];
          expectedResult = service.addCheckinToCollectionIfMissing(checkinCollection, checkin);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(checkin);
        });

        it('should add only unique Checkin to an array', () => {
          const checkinArray: ICheckin[] = [{ id: 123 }, { id: 456 }, { id: 61384 }];
          const checkinCollection: ICheckin[] = [{ id: 123 }];
          expectedResult = service.addCheckinToCollectionIfMissing(checkinCollection, ...checkinArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const checkin: ICheckin = { id: 123 };
          const checkin2: ICheckin = { id: 456 };
          expectedResult = service.addCheckinToCollectionIfMissing([], checkin, checkin2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(checkin);
          expect(expectedResult).toContain(checkin2);
        });

        it('should accept null and undefined values', () => {
          const checkin: ICheckin = { id: 123 };
          expectedResult = service.addCheckinToCollectionIfMissing([], null, checkin, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(checkin);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
