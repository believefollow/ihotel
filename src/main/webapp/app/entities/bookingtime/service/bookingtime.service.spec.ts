import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IBookingtime, Bookingtime } from '../bookingtime.model';

import { BookingtimeService } from './bookingtime.service';

describe('Service Tests', () => {
  describe('Bookingtime Service', () => {
    let service: BookingtimeService;
    let httpMock: HttpTestingController;
    let elemDefault: IBookingtime;
    let expectedResult: IBookingtime | IBookingtime[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(BookingtimeService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        bookid: 'AAAAAAA',
        roomn: 'AAAAAAA',
        booktime: currentDate,
        rtype: 'AAAAAAA',
        sl: 0,
        remark: 'AAAAAAA',
        sign: 0,
        rzsign: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            booktime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Bookingtime', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            booktime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            booktime: currentDate,
          },
          returnedFromService
        );

        service.create(new Bookingtime()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Bookingtime', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            bookid: 'BBBBBB',
            roomn: 'BBBBBB',
            booktime: currentDate.format(DATE_TIME_FORMAT),
            rtype: 'BBBBBB',
            sl: 1,
            remark: 'BBBBBB',
            sign: 1,
            rzsign: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            booktime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Bookingtime', () => {
        const patchObject = Object.assign(
          {
            bookid: 'BBBBBB',
            sl: 1,
            remark: 'BBBBBB',
          },
          new Bookingtime()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            booktime: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Bookingtime', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            bookid: 'BBBBBB',
            roomn: 'BBBBBB',
            booktime: currentDate.format(DATE_TIME_FORMAT),
            rtype: 'BBBBBB',
            sl: 1,
            remark: 'BBBBBB',
            sign: 1,
            rzsign: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            booktime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Bookingtime', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addBookingtimeToCollectionIfMissing', () => {
        it('should add a Bookingtime to an empty array', () => {
          const bookingtime: IBookingtime = { id: 123 };
          expectedResult = service.addBookingtimeToCollectionIfMissing([], bookingtime);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(bookingtime);
        });

        it('should not add a Bookingtime to an array that contains it', () => {
          const bookingtime: IBookingtime = { id: 123 };
          const bookingtimeCollection: IBookingtime[] = [
            {
              ...bookingtime,
            },
            { id: 456 },
          ];
          expectedResult = service.addBookingtimeToCollectionIfMissing(bookingtimeCollection, bookingtime);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Bookingtime to an array that doesn't contain it", () => {
          const bookingtime: IBookingtime = { id: 123 };
          const bookingtimeCollection: IBookingtime[] = [{ id: 456 }];
          expectedResult = service.addBookingtimeToCollectionIfMissing(bookingtimeCollection, bookingtime);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(bookingtime);
        });

        it('should add only unique Bookingtime to an array', () => {
          const bookingtimeArray: IBookingtime[] = [{ id: 123 }, { id: 456 }, { id: 32138 }];
          const bookingtimeCollection: IBookingtime[] = [{ id: 123 }];
          expectedResult = service.addBookingtimeToCollectionIfMissing(bookingtimeCollection, ...bookingtimeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const bookingtime: IBookingtime = { id: 123 };
          const bookingtime2: IBookingtime = { id: 456 };
          expectedResult = service.addBookingtimeToCollectionIfMissing([], bookingtime, bookingtime2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(bookingtime);
          expect(expectedResult).toContain(bookingtime2);
        });

        it('should accept null and undefined values', () => {
          const bookingtime: IBookingtime = { id: 123 };
          expectedResult = service.addBookingtimeToCollectionIfMissing([], null, bookingtime, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(bookingtime);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
