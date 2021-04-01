import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICzlCz, CzlCz } from '../czl-cz.model';

import { CzlCzService } from './czl-cz.service';

describe('Service Tests', () => {
  describe('CzlCz Service', () => {
    let service: CzlCzService;
    let httpMock: HttpTestingController;
    let elemDefault: ICzlCz;
    let expectedResult: ICzlCz | ICzlCz[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CzlCzService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        tjrq: currentDate,
        typeid: 0,
        type: 'AAAAAAA',
        fjsl: 0,
        kfl: 0,
        pjz: 0,
        ysfz: 0,
        sjfz: 0,
        fzcz: 0,
        pjzcj: 0,
        kfsM: 0,
        kflM: 0,
        pjzM: 0,
        fzsr: 0,
        dayz: 0,
        hoteltime: currentDate,
        empn: 'AAAAAAA',
        monthz: 0,
        hoteldm: 'AAAAAAA',
        isnew: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            tjrq: currentDate.format(DATE_TIME_FORMAT),
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CzlCz', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            tjrq: currentDate.format(DATE_TIME_FORMAT),
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            tjrq: currentDate,
            hoteltime: currentDate,
          },
          returnedFromService
        );

        service.create(new CzlCz()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CzlCz', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tjrq: currentDate.format(DATE_TIME_FORMAT),
            typeid: 1,
            type: 'BBBBBB',
            fjsl: 1,
            kfl: 1,
            pjz: 1,
            ysfz: 1,
            sjfz: 1,
            fzcz: 1,
            pjzcj: 1,
            kfsM: 1,
            kflM: 1,
            pjzM: 1,
            fzsr: 1,
            dayz: 1,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            empn: 'BBBBBB',
            monthz: 1,
            hoteldm: 'BBBBBB',
            isnew: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            tjrq: currentDate,
            hoteltime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CzlCz', () => {
        const patchObject = Object.assign(
          {
            tjrq: currentDate.format(DATE_TIME_FORMAT),
            typeid: 1,
            type: 'BBBBBB',
            kfl: 1,
            sjfz: 1,
            fzcz: 1,
            pjzM: 1,
            monthz: 1,
            hoteldm: 'BBBBBB',
          },
          new CzlCz()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            tjrq: currentDate,
            hoteltime: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CzlCz', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tjrq: currentDate.format(DATE_TIME_FORMAT),
            typeid: 1,
            type: 'BBBBBB',
            fjsl: 1,
            kfl: 1,
            pjz: 1,
            ysfz: 1,
            sjfz: 1,
            fzcz: 1,
            pjzcj: 1,
            kfsM: 1,
            kflM: 1,
            pjzM: 1,
            fzsr: 1,
            dayz: 1,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            empn: 'BBBBBB',
            monthz: 1,
            hoteldm: 'BBBBBB',
            isnew: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            tjrq: currentDate,
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

      it('should delete a CzlCz', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCzlCzToCollectionIfMissing', () => {
        it('should add a CzlCz to an empty array', () => {
          const czlCz: ICzlCz = { id: 123 };
          expectedResult = service.addCzlCzToCollectionIfMissing([], czlCz);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(czlCz);
        });

        it('should not add a CzlCz to an array that contains it', () => {
          const czlCz: ICzlCz = { id: 123 };
          const czlCzCollection: ICzlCz[] = [
            {
              ...czlCz,
            },
            { id: 456 },
          ];
          expectedResult = service.addCzlCzToCollectionIfMissing(czlCzCollection, czlCz);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CzlCz to an array that doesn't contain it", () => {
          const czlCz: ICzlCz = { id: 123 };
          const czlCzCollection: ICzlCz[] = [{ id: 456 }];
          expectedResult = service.addCzlCzToCollectionIfMissing(czlCzCollection, czlCz);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(czlCz);
        });

        it('should add only unique CzlCz to an array', () => {
          const czlCzArray: ICzlCz[] = [{ id: 123 }, { id: 456 }, { id: 48139 }];
          const czlCzCollection: ICzlCz[] = [{ id: 123 }];
          expectedResult = service.addCzlCzToCollectionIfMissing(czlCzCollection, ...czlCzArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const czlCz: ICzlCz = { id: 123 };
          const czlCz2: ICzlCz = { id: 456 };
          expectedResult = service.addCzlCzToCollectionIfMissing([], czlCz, czlCz2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(czlCz);
          expect(expectedResult).toContain(czlCz2);
        });

        it('should accept null and undefined values', () => {
          const czlCz: ICzlCz = { id: 123 };
          expectedResult = service.addCzlCzToCollectionIfMissing([], null, czlCz, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(czlCz);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
