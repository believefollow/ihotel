import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICheckinAccount, CheckinAccount } from '../checkin-account.model';

import { CheckinAccountService } from './checkin-account.service';

describe('Service Tests', () => {
  describe('CheckinAccount Service', () => {
    let service: CheckinAccountService;
    let httpMock: HttpTestingController;
    let elemDefault: ICheckinAccount;
    let expectedResult: ICheckinAccount | ICheckinAccount[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CheckinAccountService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        account: 'AAAAAAA',
        roomn: 'AAAAAAA',
        indatetime: currentDate,
        gotime: currentDate,
        kfang: 0,
        dhua: 0,
        minin: 0,
        peich: 0,
        qit: 0,
        total: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
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

      it('should create a CheckinAccount', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            indatetime: currentDate.format(DATE_TIME_FORMAT),
            gotime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            indatetime: currentDate,
            gotime: currentDate,
          },
          returnedFromService
        );

        service.create(new CheckinAccount()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CheckinAccount', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            account: 'BBBBBB',
            roomn: 'BBBBBB',
            indatetime: currentDate.format(DATE_TIME_FORMAT),
            gotime: currentDate.format(DATE_TIME_FORMAT),
            kfang: 1,
            dhua: 1,
            minin: 1,
            peich: 1,
            qit: 1,
            total: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should partial update a CheckinAccount', () => {
        const patchObject = Object.assign(
          {
            account: 'BBBBBB',
            roomn: 'BBBBBB',
            indatetime: currentDate.format(DATE_TIME_FORMAT),
            gotime: currentDate.format(DATE_TIME_FORMAT),
            qit: 1,
            total: 1,
          },
          new CheckinAccount()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
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

      it('should return a list of CheckinAccount', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            account: 'BBBBBB',
            roomn: 'BBBBBB',
            indatetime: currentDate.format(DATE_TIME_FORMAT),
            gotime: currentDate.format(DATE_TIME_FORMAT),
            kfang: 1,
            dhua: 1,
            minin: 1,
            peich: 1,
            qit: 1,
            total: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should delete a CheckinAccount', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCheckinAccountToCollectionIfMissing', () => {
        it('should add a CheckinAccount to an empty array', () => {
          const checkinAccount: ICheckinAccount = { id: 123 };
          expectedResult = service.addCheckinAccountToCollectionIfMissing([], checkinAccount);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(checkinAccount);
        });

        it('should not add a CheckinAccount to an array that contains it', () => {
          const checkinAccount: ICheckinAccount = { id: 123 };
          const checkinAccountCollection: ICheckinAccount[] = [
            {
              ...checkinAccount,
            },
            { id: 456 },
          ];
          expectedResult = service.addCheckinAccountToCollectionIfMissing(checkinAccountCollection, checkinAccount);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CheckinAccount to an array that doesn't contain it", () => {
          const checkinAccount: ICheckinAccount = { id: 123 };
          const checkinAccountCollection: ICheckinAccount[] = [{ id: 456 }];
          expectedResult = service.addCheckinAccountToCollectionIfMissing(checkinAccountCollection, checkinAccount);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(checkinAccount);
        });

        it('should add only unique CheckinAccount to an array', () => {
          const checkinAccountArray: ICheckinAccount[] = [{ id: 123 }, { id: 456 }, { id: 7539 }];
          const checkinAccountCollection: ICheckinAccount[] = [{ id: 123 }];
          expectedResult = service.addCheckinAccountToCollectionIfMissing(checkinAccountCollection, ...checkinAccountArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const checkinAccount: ICheckinAccount = { id: 123 };
          const checkinAccount2: ICheckinAccount = { id: 456 };
          expectedResult = service.addCheckinAccountToCollectionIfMissing([], checkinAccount, checkinAccount2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(checkinAccount);
          expect(expectedResult).toContain(checkinAccount2);
        });

        it('should accept null and undefined values', () => {
          const checkinAccount: ICheckinAccount = { id: 123 };
          expectedResult = service.addCheckinAccountToCollectionIfMissing([], null, checkinAccount, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(checkinAccount);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
