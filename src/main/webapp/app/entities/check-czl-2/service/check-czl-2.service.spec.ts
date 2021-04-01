import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICheckCzl2, CheckCzl2 } from '../check-czl-2.model';

import { CheckCzl2Service } from './check-czl-2.service';

describe('Service Tests', () => {
  describe('CheckCzl2 Service', () => {
    let service: CheckCzl2Service;
    let httpMock: HttpTestingController;
    let elemDefault: ICheckCzl2;
    let expectedResult: ICheckCzl2 | ICheckCzl2[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CheckCzl2Service);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        hoteltime: currentDate,
        protocol: 'AAAAAAA',
        rnum: 0,
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

      it('should create a CheckCzl2', () => {
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

        service.create(new CheckCzl2()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CheckCzl2', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            protocol: 'BBBBBB',
            rnum: 1,
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

      it('should partial update a CheckCzl2', () => {
        const patchObject = Object.assign(
          {
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            rnum: 1,
            empn: 'BBBBBB',
            entertime: currentDate.format(DATE_TIME_FORMAT),
          },
          new CheckCzl2()
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

      it('should return a list of CheckCzl2', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            protocol: 'BBBBBB',
            rnum: 1,
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

      it('should delete a CheckCzl2', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCheckCzl2ToCollectionIfMissing', () => {
        it('should add a CheckCzl2 to an empty array', () => {
          const checkCzl2: ICheckCzl2 = { id: 123 };
          expectedResult = service.addCheckCzl2ToCollectionIfMissing([], checkCzl2);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(checkCzl2);
        });

        it('should not add a CheckCzl2 to an array that contains it', () => {
          const checkCzl2: ICheckCzl2 = { id: 123 };
          const checkCzl2Collection: ICheckCzl2[] = [
            {
              ...checkCzl2,
            },
            { id: 456 },
          ];
          expectedResult = service.addCheckCzl2ToCollectionIfMissing(checkCzl2Collection, checkCzl2);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CheckCzl2 to an array that doesn't contain it", () => {
          const checkCzl2: ICheckCzl2 = { id: 123 };
          const checkCzl2Collection: ICheckCzl2[] = [{ id: 456 }];
          expectedResult = service.addCheckCzl2ToCollectionIfMissing(checkCzl2Collection, checkCzl2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(checkCzl2);
        });

        it('should add only unique CheckCzl2 to an array', () => {
          const checkCzl2Array: ICheckCzl2[] = [{ id: 123 }, { id: 456 }, { id: 21726 }];
          const checkCzl2Collection: ICheckCzl2[] = [{ id: 123 }];
          expectedResult = service.addCheckCzl2ToCollectionIfMissing(checkCzl2Collection, ...checkCzl2Array);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const checkCzl2: ICheckCzl2 = { id: 123 };
          const checkCzl22: ICheckCzl2 = { id: 456 };
          expectedResult = service.addCheckCzl2ToCollectionIfMissing([], checkCzl2, checkCzl22);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(checkCzl2);
          expect(expectedResult).toContain(checkCzl22);
        });

        it('should accept null and undefined values', () => {
          const checkCzl2: ICheckCzl2 = { id: 123 };
          expectedResult = service.addCheckCzl2ToCollectionIfMissing([], null, checkCzl2, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(checkCzl2);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
