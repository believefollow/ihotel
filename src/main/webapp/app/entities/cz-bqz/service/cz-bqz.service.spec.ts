import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICzBqz, CzBqz } from '../cz-bqz.model';

import { CzBqzService } from './cz-bqz.service';

describe('Service Tests', () => {
  describe('CzBqz Service', () => {
    let service: CzBqzService;
    let httpMock: HttpTestingController;
    let elemDefault: ICzBqz;
    let expectedResult: ICzBqz | ICzBqz[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CzBqzService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        rq: currentDate,
        qSl: 0,
        qKfl: 0,
        qPjz: 0,
        qYsfz: 0,
        qSjfz: 0,
        qFzcz: 0,
        qPjzcz: 0,
        bSl: 0,
        bKfl: 0,
        bPjz: 0,
        bYsfz: 0,
        bSjfz: 0,
        bFzcz: 0,
        bPjzcz: 0,
        zSl: 0,
        zKfl: 0,
        zPjz: 0,
        zYsfz: 0,
        zSjfz: 0,
        zFzcz: 0,
        zPjzcz: 0,
        zk: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            rq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CzBqz', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            rq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            rq: currentDate,
          },
          returnedFromService
        );

        service.create(new CzBqz()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CzBqz', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            rq: currentDate.format(DATE_TIME_FORMAT),
            qSl: 1,
            qKfl: 1,
            qPjz: 1,
            qYsfz: 1,
            qSjfz: 1,
            qFzcz: 1,
            qPjzcz: 1,
            bSl: 1,
            bKfl: 1,
            bPjz: 1,
            bYsfz: 1,
            bSjfz: 1,
            bFzcz: 1,
            bPjzcz: 1,
            zSl: 1,
            zKfl: 1,
            zPjz: 1,
            zYsfz: 1,
            zSjfz: 1,
            zFzcz: 1,
            zPjzcz: 1,
            zk: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            rq: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CzBqz', () => {
        const patchObject = Object.assign(
          {
            qKfl: 1,
            qSjfz: 1,
            qFzcz: 1,
            qPjzcz: 1,
            bSl: 1,
            bKfl: 1,
            bYsfz: 1,
            bSjfz: 1,
            bFzcz: 1,
            zSl: 1,
            zPjz: 1,
            zYsfz: 1,
            zSjfz: 1,
            zPjzcz: 1,
          },
          new CzBqz()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            rq: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CzBqz', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            rq: currentDate.format(DATE_TIME_FORMAT),
            qSl: 1,
            qKfl: 1,
            qPjz: 1,
            qYsfz: 1,
            qSjfz: 1,
            qFzcz: 1,
            qPjzcz: 1,
            bSl: 1,
            bKfl: 1,
            bPjz: 1,
            bYsfz: 1,
            bSjfz: 1,
            bFzcz: 1,
            bPjzcz: 1,
            zSl: 1,
            zKfl: 1,
            zPjz: 1,
            zYsfz: 1,
            zSjfz: 1,
            zFzcz: 1,
            zPjzcz: 1,
            zk: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            rq: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CzBqz', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCzBqzToCollectionIfMissing', () => {
        it('should add a CzBqz to an empty array', () => {
          const czBqz: ICzBqz = { id: 123 };
          expectedResult = service.addCzBqzToCollectionIfMissing([], czBqz);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(czBqz);
        });

        it('should not add a CzBqz to an array that contains it', () => {
          const czBqz: ICzBqz = { id: 123 };
          const czBqzCollection: ICzBqz[] = [
            {
              ...czBqz,
            },
            { id: 456 },
          ];
          expectedResult = service.addCzBqzToCollectionIfMissing(czBqzCollection, czBqz);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CzBqz to an array that doesn't contain it", () => {
          const czBqz: ICzBqz = { id: 123 };
          const czBqzCollection: ICzBqz[] = [{ id: 456 }];
          expectedResult = service.addCzBqzToCollectionIfMissing(czBqzCollection, czBqz);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(czBqz);
        });

        it('should add only unique CzBqz to an array', () => {
          const czBqzArray: ICzBqz[] = [{ id: 123 }, { id: 456 }, { id: 25005 }];
          const czBqzCollection: ICzBqz[] = [{ id: 123 }];
          expectedResult = service.addCzBqzToCollectionIfMissing(czBqzCollection, ...czBqzArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const czBqz: ICzBqz = { id: 123 };
          const czBqz2: ICzBqz = { id: 456 };
          expectedResult = service.addCzBqzToCollectionIfMissing([], czBqz, czBqz2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(czBqz);
          expect(expectedResult).toContain(czBqz2);
        });

        it('should accept null and undefined values', () => {
          const czBqz: ICzBqz = { id: 123 };
          expectedResult = service.addCzBqzToCollectionIfMissing([], null, czBqz, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(czBqz);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
