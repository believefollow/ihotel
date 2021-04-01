import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICrinfo, Crinfo } from '../crinfo.model';

import { CrinfoService } from './crinfo.service';

describe('Service Tests', () => {
  describe('Crinfo Service', () => {
    let service: CrinfoService;
    let httpMock: HttpTestingController;
    let elemDefault: ICrinfo;
    let expectedResult: ICrinfo | ICrinfo[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CrinfoService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        operatetime: currentDate,
        oldrent: 0,
        newrent: 0,
        oldroomn: 'AAAAAAA',
        newroomn: 'AAAAAAA',
        account: 'AAAAAAA',
        empn: 'AAAAAAA',
        oldday: 0,
        newday: 0,
        hoteltime: currentDate,
        roomn: 'AAAAAAA',
        memo: 'AAAAAAA',
        realname: 'AAAAAAA',
        isup: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            operatetime: currentDate.format(DATE_TIME_FORMAT),
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Crinfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            operatetime: currentDate.format(DATE_TIME_FORMAT),
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            operatetime: currentDate,
            hoteltime: currentDate,
          },
          returnedFromService
        );

        service.create(new Crinfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Crinfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            operatetime: currentDate.format(DATE_TIME_FORMAT),
            oldrent: 1,
            newrent: 1,
            oldroomn: 'BBBBBB',
            newroomn: 'BBBBBB',
            account: 'BBBBBB',
            empn: 'BBBBBB',
            oldday: 1,
            newday: 1,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            roomn: 'BBBBBB',
            memo: 'BBBBBB',
            realname: 'BBBBBB',
            isup: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            operatetime: currentDate,
            hoteltime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Crinfo', () => {
        const patchObject = Object.assign(
          {
            oldrent: 1,
            newroomn: 'BBBBBB',
            empn: 'BBBBBB',
            oldday: 1,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            memo: 'BBBBBB',
          },
          new Crinfo()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            operatetime: currentDate,
            hoteltime: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Crinfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            operatetime: currentDate.format(DATE_TIME_FORMAT),
            oldrent: 1,
            newrent: 1,
            oldroomn: 'BBBBBB',
            newroomn: 'BBBBBB',
            account: 'BBBBBB',
            empn: 'BBBBBB',
            oldday: 1,
            newday: 1,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            roomn: 'BBBBBB',
            memo: 'BBBBBB',
            realname: 'BBBBBB',
            isup: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            operatetime: currentDate,
            hoteltime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Crinfo', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCrinfoToCollectionIfMissing', () => {
        it('should add a Crinfo to an empty array', () => {
          const crinfo: ICrinfo = { id: 123 };
          expectedResult = service.addCrinfoToCollectionIfMissing([], crinfo);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(crinfo);
        });

        it('should not add a Crinfo to an array that contains it', () => {
          const crinfo: ICrinfo = { id: 123 };
          const crinfoCollection: ICrinfo[] = [
            {
              ...crinfo,
            },
            { id: 456 },
          ];
          expectedResult = service.addCrinfoToCollectionIfMissing(crinfoCollection, crinfo);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Crinfo to an array that doesn't contain it", () => {
          const crinfo: ICrinfo = { id: 123 };
          const crinfoCollection: ICrinfo[] = [{ id: 456 }];
          expectedResult = service.addCrinfoToCollectionIfMissing(crinfoCollection, crinfo);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(crinfo);
        });

        it('should add only unique Crinfo to an array', () => {
          const crinfoArray: ICrinfo[] = [{ id: 123 }, { id: 456 }, { id: 92895 }];
          const crinfoCollection: ICrinfo[] = [{ id: 123 }];
          expectedResult = service.addCrinfoToCollectionIfMissing(crinfoCollection, ...crinfoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const crinfo: ICrinfo = { id: 123 };
          const crinfo2: ICrinfo = { id: 456 };
          expectedResult = service.addCrinfoToCollectionIfMissing([], crinfo, crinfo2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(crinfo);
          expect(expectedResult).toContain(crinfo2);
        });

        it('should accept null and undefined values', () => {
          const crinfo: ICrinfo = { id: 123 };
          expectedResult = service.addCrinfoToCollectionIfMissing([], null, crinfo, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(crinfo);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
