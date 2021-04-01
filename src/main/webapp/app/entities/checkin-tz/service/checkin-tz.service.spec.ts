import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICheckinTz, CheckinTz } from '../checkin-tz.model';

import { CheckinTzService } from './checkin-tz.service';

describe('Service Tests', () => {
  describe('CheckinTz Service', () => {
    let service: CheckinTzService;
    let httpMock: HttpTestingController;
    let elemDefault: ICheckinTz;
    let expectedResult: ICheckinTz | ICheckinTz[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CheckinTzService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        guestId: 0,
        account: 'AAAAAAA',
        hoteltime: currentDate,
        indatetime: currentDate,
        residefate: 0,
        gotime: currentDate,
        empn: 'AAAAAAA',
        roomn: 'AAAAAAA',
        rentp: 'AAAAAAA',
        protocolrent: 0,
        remark: 'AAAAAAA',
        phonen: 'AAAAAAA',
        empn2: 'AAAAAAA',
        memo: 'AAAAAAA',
        lfSign: 'AAAAAAA',
        guestname: 'AAAAAAA',
        bc: 'AAAAAAA',
        roomtype: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            indatetime: currentDate.format(DATE_TIME_FORMAT),
            gotime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CheckinTz', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            indatetime: currentDate.format(DATE_TIME_FORMAT),
            gotime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            indatetime: currentDate,
            gotime: currentDate,
          },
          returnedFromService
        );

        service.create(new CheckinTz()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CheckinTz', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            guestId: 1,
            account: 'BBBBBB',
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            indatetime: currentDate.format(DATE_TIME_FORMAT),
            residefate: 1,
            gotime: currentDate.format(DATE_TIME_FORMAT),
            empn: 'BBBBBB',
            roomn: 'BBBBBB',
            rentp: 'BBBBBB',
            protocolrent: 1,
            remark: 'BBBBBB',
            phonen: 'BBBBBB',
            empn2: 'BBBBBB',
            memo: 'BBBBBB',
            lfSign: 'BBBBBB',
            guestname: 'BBBBBB',
            bc: 'BBBBBB',
            roomtype: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            indatetime: currentDate,
            gotime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CheckinTz', () => {
        const patchObject = Object.assign(
          {
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            residefate: 1,
            empn: 'BBBBBB',
            roomn: 'BBBBBB',
            rentp: 'BBBBBB',
            remark: 'BBBBBB',
            phonen: 'BBBBBB',
            memo: 'BBBBBB',
            lfSign: 'BBBBBB',
            bc: 'BBBBBB',
            roomtype: 'BBBBBB',
          },
          new CheckinTz()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            indatetime: currentDate,
            gotime: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CheckinTz', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            guestId: 1,
            account: 'BBBBBB',
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            indatetime: currentDate.format(DATE_TIME_FORMAT),
            residefate: 1,
            gotime: currentDate.format(DATE_TIME_FORMAT),
            empn: 'BBBBBB',
            roomn: 'BBBBBB',
            rentp: 'BBBBBB',
            protocolrent: 1,
            remark: 'BBBBBB',
            phonen: 'BBBBBB',
            empn2: 'BBBBBB',
            memo: 'BBBBBB',
            lfSign: 'BBBBBB',
            guestname: 'BBBBBB',
            bc: 'BBBBBB',
            roomtype: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            indatetime: currentDate,
            gotime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CheckinTz', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCheckinTzToCollectionIfMissing', () => {
        it('should add a CheckinTz to an empty array', () => {
          const checkinTz: ICheckinTz = { id: 123 };
          expectedResult = service.addCheckinTzToCollectionIfMissing([], checkinTz);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(checkinTz);
        });

        it('should not add a CheckinTz to an array that contains it', () => {
          const checkinTz: ICheckinTz = { id: 123 };
          const checkinTzCollection: ICheckinTz[] = [
            {
              ...checkinTz,
            },
            { id: 456 },
          ];
          expectedResult = service.addCheckinTzToCollectionIfMissing(checkinTzCollection, checkinTz);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CheckinTz to an array that doesn't contain it", () => {
          const checkinTz: ICheckinTz = { id: 123 };
          const checkinTzCollection: ICheckinTz[] = [{ id: 456 }];
          expectedResult = service.addCheckinTzToCollectionIfMissing(checkinTzCollection, checkinTz);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(checkinTz);
        });

        it('should add only unique CheckinTz to an array', () => {
          const checkinTzArray: ICheckinTz[] = [{ id: 123 }, { id: 456 }, { id: 90328 }];
          const checkinTzCollection: ICheckinTz[] = [{ id: 123 }];
          expectedResult = service.addCheckinTzToCollectionIfMissing(checkinTzCollection, ...checkinTzArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const checkinTz: ICheckinTz = { id: 123 };
          const checkinTz2: ICheckinTz = { id: 456 };
          expectedResult = service.addCheckinTzToCollectionIfMissing([], checkinTz, checkinTz2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(checkinTz);
          expect(expectedResult).toContain(checkinTz2);
        });

        it('should accept null and undefined values', () => {
          const checkinTz: ICheckinTz = { id: 123 };
          expectedResult = service.addCheckinTzToCollectionIfMissing([], null, checkinTz, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(checkinTz);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
