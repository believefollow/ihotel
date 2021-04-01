import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDayearndetail, Dayearndetail } from '../dayearndetail.model';

import { DayearndetailService } from './dayearndetail.service';

describe('Service Tests', () => {
  describe('Dayearndetail Service', () => {
    let service: DayearndetailService;
    let httpMock: HttpTestingController;
    let elemDefault: IDayearndetail;
    let expectedResult: IDayearndetail | IDayearndetail[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DayearndetailService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        earndate: currentDate,
        salespotn: 0,
        money: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            earndate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Dayearndetail', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            earndate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            earndate: currentDate,
          },
          returnedFromService
        );

        service.create(new Dayearndetail()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Dayearndetail', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            earndate: currentDate.format(DATE_TIME_FORMAT),
            salespotn: 1,
            money: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            earndate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Dayearndetail', () => {
        const patchObject = Object.assign(
          {
            salespotn: 1,
          },
          new Dayearndetail()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            earndate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Dayearndetail', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            earndate: currentDate.format(DATE_TIME_FORMAT),
            salespotn: 1,
            money: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            earndate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Dayearndetail', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDayearndetailToCollectionIfMissing', () => {
        it('should add a Dayearndetail to an empty array', () => {
          const dayearndetail: IDayearndetail = { id: 123 };
          expectedResult = service.addDayearndetailToCollectionIfMissing([], dayearndetail);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dayearndetail);
        });

        it('should not add a Dayearndetail to an array that contains it', () => {
          const dayearndetail: IDayearndetail = { id: 123 };
          const dayearndetailCollection: IDayearndetail[] = [
            {
              ...dayearndetail,
            },
            { id: 456 },
          ];
          expectedResult = service.addDayearndetailToCollectionIfMissing(dayearndetailCollection, dayearndetail);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Dayearndetail to an array that doesn't contain it", () => {
          const dayearndetail: IDayearndetail = { id: 123 };
          const dayearndetailCollection: IDayearndetail[] = [{ id: 456 }];
          expectedResult = service.addDayearndetailToCollectionIfMissing(dayearndetailCollection, dayearndetail);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dayearndetail);
        });

        it('should add only unique Dayearndetail to an array', () => {
          const dayearndetailArray: IDayearndetail[] = [{ id: 123 }, { id: 456 }, { id: 22454 }];
          const dayearndetailCollection: IDayearndetail[] = [{ id: 123 }];
          expectedResult = service.addDayearndetailToCollectionIfMissing(dayearndetailCollection, ...dayearndetailArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dayearndetail: IDayearndetail = { id: 123 };
          const dayearndetail2: IDayearndetail = { id: 456 };
          expectedResult = service.addDayearndetailToCollectionIfMissing([], dayearndetail, dayearndetail2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dayearndetail);
          expect(expectedResult).toContain(dayearndetail2);
        });

        it('should accept null and undefined values', () => {
          const dayearndetail: IDayearndetail = { id: 123 };
          expectedResult = service.addDayearndetailToCollectionIfMissing([], null, dayearndetail, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dayearndetail);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
