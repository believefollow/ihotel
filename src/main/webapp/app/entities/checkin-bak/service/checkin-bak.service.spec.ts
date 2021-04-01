import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICheckinBak, CheckinBak } from '../checkin-bak.model';

import { CheckinBakService } from './checkin-bak.service';

describe('Service Tests', () => {
  describe('CheckinBak Service', () => {
    let service: CheckinBakService;
    let httpMock: HttpTestingController;
    let elemDefault: ICheckinBak;
    let expectedResult: ICheckinBak | ICheckinBak[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CheckinBakService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
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
        comeinfo: 'AAAAAAA',
        goinfo: 'AAAAAAA',
        phonen: 'AAAAAAA',
        empn2: 'AAAAAAA',
        adhoc: 'AAAAAAA',
        auditflag: 0,
        groupn: 'AAAAAAA',
        payment: 'AAAAAAA',
        mtype: 'AAAAAAA',
        memo: 'AAAAAAA',
        flight: 'AAAAAAA',
        credit: 0,
        talklevel: 'AAAAAAA',
        lfSign: 'AAAAAAA',
        keynum: 'AAAAAAA',
        icNum: 0,
        bh: 0,
        icOwner: 'AAAAAAA',
        markId: 'AAAAAAA',
        gj: 'AAAAAAA',
        yfj: 0,
        hoteldate: currentDate,
        id: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            indatetime: currentDate.format(DATE_TIME_FORMAT),
            gotime: currentDate.format(DATE_TIME_FORMAT),
            hoteldate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CheckinBak', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            indatetime: currentDate.format(DATE_TIME_FORMAT),
            gotime: currentDate.format(DATE_TIME_FORMAT),
            hoteldate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            indatetime: currentDate,
            gotime: currentDate,
            hoteldate: currentDate,
          },
          returnedFromService
        );

        service.create(new CheckinBak()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CheckinBak', () => {
        const returnedFromService = Object.assign(
          {
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
            comeinfo: 'BBBBBB',
            goinfo: 'BBBBBB',
            phonen: 'BBBBBB',
            empn2: 'BBBBBB',
            adhoc: 'BBBBBB',
            auditflag: 1,
            groupn: 'BBBBBB',
            payment: 'BBBBBB',
            mtype: 'BBBBBB',
            memo: 'BBBBBB',
            flight: 'BBBBBB',
            credit: 1,
            talklevel: 'BBBBBB',
            lfSign: 'BBBBBB',
            keynum: 'BBBBBB',
            icNum: 1,
            bh: 1,
            icOwner: 'BBBBBB',
            markId: 'BBBBBB',
            gj: 'BBBBBB',
            yfj: 1,
            hoteldate: currentDate.format(DATE_TIME_FORMAT),
            id: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            indatetime: currentDate,
            gotime: currentDate,
            hoteldate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CheckinBak', () => {
        const patchObject = Object.assign(
          {
            indatetime: currentDate.format(DATE_TIME_FORMAT),
            empn: 'BBBBBB',
            rentp: 'BBBBBB',
            comeinfo: 'BBBBBB',
            phonen: 'BBBBBB',
            flight: 'BBBBBB',
            talklevel: 'BBBBBB',
            lfSign: 'BBBBBB',
            keynum: 'BBBBBB',
            bh: 1,
            yfj: 1,
            hoteldate: currentDate.format(DATE_TIME_FORMAT),
          },
          new CheckinBak()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            indatetime: currentDate,
            gotime: currentDate,
            hoteldate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CheckinBak', () => {
        const returnedFromService = Object.assign(
          {
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
            comeinfo: 'BBBBBB',
            goinfo: 'BBBBBB',
            phonen: 'BBBBBB',
            empn2: 'BBBBBB',
            adhoc: 'BBBBBB',
            auditflag: 1,
            groupn: 'BBBBBB',
            payment: 'BBBBBB',
            mtype: 'BBBBBB',
            memo: 'BBBBBB',
            flight: 'BBBBBB',
            credit: 1,
            talklevel: 'BBBBBB',
            lfSign: 'BBBBBB',
            keynum: 'BBBBBB',
            icNum: 1,
            bh: 1,
            icOwner: 'BBBBBB',
            markId: 'BBBBBB',
            gj: 'BBBBBB',
            yfj: 1,
            hoteldate: currentDate.format(DATE_TIME_FORMAT),
            id: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            indatetime: currentDate,
            gotime: currentDate,
            hoteldate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CheckinBak', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCheckinBakToCollectionIfMissing', () => {
        it('should add a CheckinBak to an empty array', () => {
          const checkinBak: ICheckinBak = { id: 123 };
          expectedResult = service.addCheckinBakToCollectionIfMissing([], checkinBak);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(checkinBak);
        });

        it('should not add a CheckinBak to an array that contains it', () => {
          const checkinBak: ICheckinBak = { id: 123 };
          const checkinBakCollection: ICheckinBak[] = [
            {
              ...checkinBak,
            },
            { id: 456 },
          ];
          expectedResult = service.addCheckinBakToCollectionIfMissing(checkinBakCollection, checkinBak);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CheckinBak to an array that doesn't contain it", () => {
          const checkinBak: ICheckinBak = { id: 123 };
          const checkinBakCollection: ICheckinBak[] = [{ id: 456 }];
          expectedResult = service.addCheckinBakToCollectionIfMissing(checkinBakCollection, checkinBak);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(checkinBak);
        });

        it('should add only unique CheckinBak to an array', () => {
          const checkinBakArray: ICheckinBak[] = [{ id: 123 }, { id: 456 }, { id: 55172 }];
          const checkinBakCollection: ICheckinBak[] = [{ id: 123 }];
          expectedResult = service.addCheckinBakToCollectionIfMissing(checkinBakCollection, ...checkinBakArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const checkinBak: ICheckinBak = { id: 123 };
          const checkinBak2: ICheckinBak = { id: 456 };
          expectedResult = service.addCheckinBakToCollectionIfMissing([], checkinBak, checkinBak2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(checkinBak);
          expect(expectedResult).toContain(checkinBak2);
        });

        it('should accept null and undefined values', () => {
          const checkinBak: ICheckinBak = { id: 123 };
          expectedResult = service.addCheckinBakToCollectionIfMissing([], null, checkinBak, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(checkinBak);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
