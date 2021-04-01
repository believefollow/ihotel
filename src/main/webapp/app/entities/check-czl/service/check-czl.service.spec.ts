import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICheckCzl, CheckCzl } from '../check-czl.model';

import { CheckCzlService } from './check-czl.service';

describe('Service Tests', () => {
  describe('CheckCzl Service', () => {
    let service: CheckCzlService;
    let httpMock: HttpTestingController;
    let elemDefault: ICheckCzl;
    let expectedResult: ICheckCzl | ICheckCzl[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CheckCzlService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        hoteltime: currentDate,
        rtype: 'AAAAAAA',
        rnum: 0,
        rOutnum: 0,
        czl: 0,
        chagrge: 0,
        chagrgeAvg: 0,
        empn: 'AAAAAAA',
        entertime: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            entertime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CheckCzl', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            entertime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            entertime: currentDate,
          },
          returnedFromService
        );

        service.create(new CheckCzl()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CheckCzl', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            rtype: 'BBBBBB',
            rnum: 1,
            rOutnum: 1,
            czl: 1,
            chagrge: 1,
            chagrgeAvg: 1,
            empn: 'BBBBBB',
            entertime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            entertime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CheckCzl', () => {
        const patchObject = Object.assign(
          {
            czl: 1,
            chagrgeAvg: 1,
            empn: 'BBBBBB',
            entertime: currentDate.format(DATE_TIME_FORMAT),
          },
          new CheckCzl()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            entertime: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CheckCzl', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            rtype: 'BBBBBB',
            rnum: 1,
            rOutnum: 1,
            czl: 1,
            chagrge: 1,
            chagrgeAvg: 1,
            empn: 'BBBBBB',
            entertime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            entertime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CheckCzl', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCheckCzlToCollectionIfMissing', () => {
        it('should add a CheckCzl to an empty array', () => {
          const checkCzl: ICheckCzl = { id: 123 };
          expectedResult = service.addCheckCzlToCollectionIfMissing([], checkCzl);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(checkCzl);
        });

        it('should not add a CheckCzl to an array that contains it', () => {
          const checkCzl: ICheckCzl = { id: 123 };
          const checkCzlCollection: ICheckCzl[] = [
            {
              ...checkCzl,
            },
            { id: 456 },
          ];
          expectedResult = service.addCheckCzlToCollectionIfMissing(checkCzlCollection, checkCzl);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CheckCzl to an array that doesn't contain it", () => {
          const checkCzl: ICheckCzl = { id: 123 };
          const checkCzlCollection: ICheckCzl[] = [{ id: 456 }];
          expectedResult = service.addCheckCzlToCollectionIfMissing(checkCzlCollection, checkCzl);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(checkCzl);
        });

        it('should add only unique CheckCzl to an array', () => {
          const checkCzlArray: ICheckCzl[] = [{ id: 123 }, { id: 456 }, { id: 64006 }];
          const checkCzlCollection: ICheckCzl[] = [{ id: 123 }];
          expectedResult = service.addCheckCzlToCollectionIfMissing(checkCzlCollection, ...checkCzlArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const checkCzl: ICheckCzl = { id: 123 };
          const checkCzl2: ICheckCzl = { id: 456 };
          expectedResult = service.addCheckCzlToCollectionIfMissing([], checkCzl, checkCzl2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(checkCzl);
          expect(expectedResult).toContain(checkCzl2);
        });

        it('should accept null and undefined values', () => {
          const checkCzl: ICheckCzl = { id: 123 };
          expectedResult = service.addCheckCzlToCollectionIfMissing([], null, checkCzl, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(checkCzl);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
