import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAccounts, Accounts } from '../accounts.model';

import { AccountsService } from './accounts.service';

describe('Service Tests', () => {
  describe('Accounts Service', () => {
    let service: AccountsService;
    let httpMock: HttpTestingController;
    let elemDefault: IAccounts;
    let expectedResult: IAccounts | IAccounts[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AccountsService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        account: 'AAAAAAA',
        consumetime: currentDate,
        hoteltime: currentDate,
        feenum: 0,
        money: 0,
        memo: 'AAAAAAA',
        empn: 'AAAAAAA',
        imprest: 0,
        propertiy: 'AAAAAAA',
        earntypen: 0,
        payment: 0,
        roomn: 'AAAAAAA',
        id: 0,
        ulogogram: 'AAAAAAA',
        lk: 0,
        acc: 'AAAAAAA',
        jzSign: 0,
        jzflag: 0,
        sign: 'AAAAAAA',
        bs: 0,
        jzhotel: currentDate,
        jzempn: 'AAAAAAA',
        jztime: currentDate,
        chonghong: 0,
        billno: 'AAAAAAA',
        printcount: 0,
        vipjf: 0,
        hykh: 'AAAAAAA',
        sl: 0,
        sgdjh: 'AAAAAAA',
        hoteldm: 'AAAAAAA',
        isnew: 0,
        guestId: 0,
        yhkh: 'AAAAAAA',
        djq: 'AAAAAAA',
        ysje: 0,
        bj: 'AAAAAAA',
        bjempn: 'AAAAAAA',
        bjtime: currentDate,
        paper2: 'AAAAAAA',
        bc: 'AAAAAAA',
        auto: 'AAAAAAA',
        xsy: 'AAAAAAA',
        djkh: 'AAAAAAA',
        djsign: 'AAAAAAA',
        classname: 'AAAAAAA',
        iscy: 'AAAAAAA',
        bsign: 'AAAAAAA',
        fx: 'AAAAAAA',
        djlx: 'AAAAAAA',
        isup: 0,
        yongjin: 0,
        czpc: 'AAAAAAA',
        cxflag: 0,
        pmemo: 'AAAAAAA',
        czbillno: 'AAAAAAA',
        djqbz: 'AAAAAAA',
        ysqmemo: 'AAAAAAA',
        transactionId: 'AAAAAAA',
        outTradeNo: 'AAAAAAA',
        gsname: 'AAAAAAA',
        rz: currentDate,
        gz: currentDate,
        ts: 0,
        ky: 'AAAAAAA',
        xy: 'AAAAAAA',
        roomtype: 'AAAAAAA',
        bkid: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            consumetime: currentDate.format(DATE_TIME_FORMAT),
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            jzhotel: currentDate.format(DATE_TIME_FORMAT),
            jztime: currentDate.format(DATE_TIME_FORMAT),
            bjtime: currentDate.format(DATE_TIME_FORMAT),
            rz: currentDate.format(DATE_TIME_FORMAT),
            gz: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Accounts', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            consumetime: currentDate.format(DATE_TIME_FORMAT),
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            jzhotel: currentDate.format(DATE_TIME_FORMAT),
            jztime: currentDate.format(DATE_TIME_FORMAT),
            bjtime: currentDate.format(DATE_TIME_FORMAT),
            rz: currentDate.format(DATE_TIME_FORMAT),
            gz: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            consumetime: currentDate,
            hoteltime: currentDate,
            jzhotel: currentDate,
            jztime: currentDate,
            bjtime: currentDate,
            rz: currentDate,
            gz: currentDate,
          },
          returnedFromService
        );

        service.create(new Accounts()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Accounts', () => {
        const returnedFromService = Object.assign(
          {
            account: 'BBBBBB',
            consumetime: currentDate.format(DATE_TIME_FORMAT),
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            feenum: 1,
            money: 1,
            memo: 'BBBBBB',
            empn: 'BBBBBB',
            imprest: 1,
            propertiy: 'BBBBBB',
            earntypen: 1,
            payment: 1,
            roomn: 'BBBBBB',
            id: 1,
            ulogogram: 'BBBBBB',
            lk: 1,
            acc: 'BBBBBB',
            jzSign: 1,
            jzflag: 1,
            sign: 'BBBBBB',
            bs: 1,
            jzhotel: currentDate.format(DATE_TIME_FORMAT),
            jzempn: 'BBBBBB',
            jztime: currentDate.format(DATE_TIME_FORMAT),
            chonghong: 1,
            billno: 'BBBBBB',
            printcount: 1,
            vipjf: 1,
            hykh: 'BBBBBB',
            sl: 1,
            sgdjh: 'BBBBBB',
            hoteldm: 'BBBBBB',
            isnew: 1,
            guestId: 1,
            yhkh: 'BBBBBB',
            djq: 'BBBBBB',
            ysje: 1,
            bj: 'BBBBBB',
            bjempn: 'BBBBBB',
            bjtime: currentDate.format(DATE_TIME_FORMAT),
            paper2: 'BBBBBB',
            bc: 'BBBBBB',
            auto: 'BBBBBB',
            xsy: 'BBBBBB',
            djkh: 'BBBBBB',
            djsign: 'BBBBBB',
            classname: 'BBBBBB',
            iscy: 'BBBBBB',
            bsign: 'BBBBBB',
            fx: 'BBBBBB',
            djlx: 'BBBBBB',
            isup: 1,
            yongjin: 1,
            czpc: 'BBBBBB',
            cxflag: 1,
            pmemo: 'BBBBBB',
            czbillno: 'BBBBBB',
            djqbz: 'BBBBBB',
            ysqmemo: 'BBBBBB',
            transactionId: 'BBBBBB',
            outTradeNo: 'BBBBBB',
            gsname: 'BBBBBB',
            rz: currentDate.format(DATE_TIME_FORMAT),
            gz: currentDate.format(DATE_TIME_FORMAT),
            ts: 1,
            ky: 'BBBBBB',
            xy: 'BBBBBB',
            roomtype: 'BBBBBB',
            bkid: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            consumetime: currentDate,
            hoteltime: currentDate,
            jzhotel: currentDate,
            jztime: currentDate,
            bjtime: currentDate,
            rz: currentDate,
            gz: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Accounts', () => {
        const patchObject = Object.assign(
          {
            account: 'BBBBBB',
            consumetime: currentDate.format(DATE_TIME_FORMAT),
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            money: 1,
            imprest: 1,
            ulogogram: 'BBBBBB',
            acc: 'BBBBBB',
            jzSign: 1,
            bs: 1,
            jzhotel: currentDate.format(DATE_TIME_FORMAT),
            jzempn: 'BBBBBB',
            jztime: currentDate.format(DATE_TIME_FORMAT),
            chonghong: 1,
            billno: 'BBBBBB',
            printcount: 1,
            vipjf: 1,
            sl: 1,
            sgdjh: 'BBBBBB',
            isnew: 1,
            guestId: 1,
            yhkh: 'BBBBBB',
            djq: 'BBBBBB',
            ysje: 1,
            bjtime: currentDate.format(DATE_TIME_FORMAT),
            xsy: 'BBBBBB',
            iscy: 'BBBBBB',
            fx: 'BBBBBB',
            isup: 1,
            yongjin: 1,
            pmemo: 'BBBBBB',
            djqbz: 'BBBBBB',
            transactionId: 'BBBBBB',
            outTradeNo: 'BBBBBB',
            gsname: 'BBBBBB',
            rz: currentDate.format(DATE_TIME_FORMAT),
            gz: currentDate.format(DATE_TIME_FORMAT),
            xy: 'BBBBBB',
          },
          new Accounts()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            consumetime: currentDate,
            hoteltime: currentDate,
            jzhotel: currentDate,
            jztime: currentDate,
            bjtime: currentDate,
            rz: currentDate,
            gz: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Accounts', () => {
        const returnedFromService = Object.assign(
          {
            account: 'BBBBBB',
            consumetime: currentDate.format(DATE_TIME_FORMAT),
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            feenum: 1,
            money: 1,
            memo: 'BBBBBB',
            empn: 'BBBBBB',
            imprest: 1,
            propertiy: 'BBBBBB',
            earntypen: 1,
            payment: 1,
            roomn: 'BBBBBB',
            id: 1,
            ulogogram: 'BBBBBB',
            lk: 1,
            acc: 'BBBBBB',
            jzSign: 1,
            jzflag: 1,
            sign: 'BBBBBB',
            bs: 1,
            jzhotel: currentDate.format(DATE_TIME_FORMAT),
            jzempn: 'BBBBBB',
            jztime: currentDate.format(DATE_TIME_FORMAT),
            chonghong: 1,
            billno: 'BBBBBB',
            printcount: 1,
            vipjf: 1,
            hykh: 'BBBBBB',
            sl: 1,
            sgdjh: 'BBBBBB',
            hoteldm: 'BBBBBB',
            isnew: 1,
            guestId: 1,
            yhkh: 'BBBBBB',
            djq: 'BBBBBB',
            ysje: 1,
            bj: 'BBBBBB',
            bjempn: 'BBBBBB',
            bjtime: currentDate.format(DATE_TIME_FORMAT),
            paper2: 'BBBBBB',
            bc: 'BBBBBB',
            auto: 'BBBBBB',
            xsy: 'BBBBBB',
            djkh: 'BBBBBB',
            djsign: 'BBBBBB',
            classname: 'BBBBBB',
            iscy: 'BBBBBB',
            bsign: 'BBBBBB',
            fx: 'BBBBBB',
            djlx: 'BBBBBB',
            isup: 1,
            yongjin: 1,
            czpc: 'BBBBBB',
            cxflag: 1,
            pmemo: 'BBBBBB',
            czbillno: 'BBBBBB',
            djqbz: 'BBBBBB',
            ysqmemo: 'BBBBBB',
            transactionId: 'BBBBBB',
            outTradeNo: 'BBBBBB',
            gsname: 'BBBBBB',
            rz: currentDate.format(DATE_TIME_FORMAT),
            gz: currentDate.format(DATE_TIME_FORMAT),
            ts: 1,
            ky: 'BBBBBB',
            xy: 'BBBBBB',
            roomtype: 'BBBBBB',
            bkid: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            consumetime: currentDate,
            hoteltime: currentDate,
            jzhotel: currentDate,
            jztime: currentDate,
            bjtime: currentDate,
            rz: currentDate,
            gz: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Accounts', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAccountsToCollectionIfMissing', () => {
        it('should add a Accounts to an empty array', () => {
          const accounts: IAccounts = { id: 123 };
          expectedResult = service.addAccountsToCollectionIfMissing([], accounts);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(accounts);
        });

        it('should not add a Accounts to an array that contains it', () => {
          const accounts: IAccounts = { id: 123 };
          const accountsCollection: IAccounts[] = [
            {
              ...accounts,
            },
            { id: 456 },
          ];
          expectedResult = service.addAccountsToCollectionIfMissing(accountsCollection, accounts);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Accounts to an array that doesn't contain it", () => {
          const accounts: IAccounts = { id: 123 };
          const accountsCollection: IAccounts[] = [{ id: 456 }];
          expectedResult = service.addAccountsToCollectionIfMissing(accountsCollection, accounts);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(accounts);
        });

        it('should add only unique Accounts to an array', () => {
          const accountsArray: IAccounts[] = [{ id: 123 }, { id: 456 }, { id: 95561 }];
          const accountsCollection: IAccounts[] = [{ id: 123 }];
          expectedResult = service.addAccountsToCollectionIfMissing(accountsCollection, ...accountsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const accounts: IAccounts = { id: 123 };
          const accounts2: IAccounts = { id: 456 };
          expectedResult = service.addAccountsToCollectionIfMissing([], accounts, accounts2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(accounts);
          expect(expectedResult).toContain(accounts2);
        });

        it('should accept null and undefined values', () => {
          const accounts: IAccounts = { id: 123 };
          expectedResult = service.addAccountsToCollectionIfMissing([], null, accounts, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(accounts);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
