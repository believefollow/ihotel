import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICardysq, Cardysq } from '../cardysq.model';

import { CardysqService } from './cardysq.service';

describe('Service Tests', () => {
  describe('Cardysq Service', () => {
    let service: CardysqService;
    let httpMock: HttpTestingController;
    let elemDefault: ICardysq;
    let expectedResult: ICardysq | ICardysq[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CardysqService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        roomn: 'AAAAAAA',
        guestname: 'AAAAAAA',
        account: 'AAAAAAA',
        rq: currentDate,
        cardid: 'AAAAAAA',
        djh: 'AAAAAAA',
        sqh: 'AAAAAAA',
        empn: 'AAAAAAA',
        sign: 'AAAAAAA',
        hoteltime: currentDate,
        yxrq: currentDate,
        je: 0,
        ysqmemo: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            rq: currentDate.format(DATE_TIME_FORMAT),
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            yxrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Cardysq', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            rq: currentDate.format(DATE_TIME_FORMAT),
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            yxrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            rq: currentDate,
            hoteltime: currentDate,
            yxrq: currentDate,
          },
          returnedFromService
        );

        service.create(new Cardysq()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Cardysq', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            roomn: 'BBBBBB',
            guestname: 'BBBBBB',
            account: 'BBBBBB',
            rq: currentDate.format(DATE_TIME_FORMAT),
            cardid: 'BBBBBB',
            djh: 'BBBBBB',
            sqh: 'BBBBBB',
            empn: 'BBBBBB',
            sign: 'BBBBBB',
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            yxrq: currentDate.format(DATE_TIME_FORMAT),
            je: 1,
            ysqmemo: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            rq: currentDate,
            hoteltime: currentDate,
            yxrq: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Cardysq', () => {
        const patchObject = Object.assign(
          {
            roomn: 'BBBBBB',
            guestname: 'BBBBBB',
            account: 'BBBBBB',
            cardid: 'BBBBBB',
            djh: 'BBBBBB',
            sqh: 'BBBBBB',
            sign: 'BBBBBB',
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            yxrq: currentDate.format(DATE_TIME_FORMAT),
            ysqmemo: 'BBBBBB',
          },
          new Cardysq()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            rq: currentDate,
            hoteltime: currentDate,
            yxrq: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Cardysq', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            roomn: 'BBBBBB',
            guestname: 'BBBBBB',
            account: 'BBBBBB',
            rq: currentDate.format(DATE_TIME_FORMAT),
            cardid: 'BBBBBB',
            djh: 'BBBBBB',
            sqh: 'BBBBBB',
            empn: 'BBBBBB',
            sign: 'BBBBBB',
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            yxrq: currentDate.format(DATE_TIME_FORMAT),
            je: 1,
            ysqmemo: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            rq: currentDate,
            hoteltime: currentDate,
            yxrq: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Cardysq', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCardysqToCollectionIfMissing', () => {
        it('should add a Cardysq to an empty array', () => {
          const cardysq: ICardysq = { id: 123 };
          expectedResult = service.addCardysqToCollectionIfMissing([], cardysq);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cardysq);
        });

        it('should not add a Cardysq to an array that contains it', () => {
          const cardysq: ICardysq = { id: 123 };
          const cardysqCollection: ICardysq[] = [
            {
              ...cardysq,
            },
            { id: 456 },
          ];
          expectedResult = service.addCardysqToCollectionIfMissing(cardysqCollection, cardysq);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Cardysq to an array that doesn't contain it", () => {
          const cardysq: ICardysq = { id: 123 };
          const cardysqCollection: ICardysq[] = [{ id: 456 }];
          expectedResult = service.addCardysqToCollectionIfMissing(cardysqCollection, cardysq);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cardysq);
        });

        it('should add only unique Cardysq to an array', () => {
          const cardysqArray: ICardysq[] = [{ id: 123 }, { id: 456 }, { id: 48096 }];
          const cardysqCollection: ICardysq[] = [{ id: 123 }];
          expectedResult = service.addCardysqToCollectionIfMissing(cardysqCollection, ...cardysqArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const cardysq: ICardysq = { id: 123 };
          const cardysq2: ICardysq = { id: 456 };
          expectedResult = service.addCardysqToCollectionIfMissing([], cardysq, cardysq2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cardysq);
          expect(expectedResult).toContain(cardysq2);
        });

        it('should accept null and undefined values', () => {
          const cardysq: ICardysq = { id: 123 };
          expectedResult = service.addCardysqToCollectionIfMissing([], null, cardysq, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cardysq);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
