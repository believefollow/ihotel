import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IClog, Clog } from '../clog.model';

import { ClogService } from './clog.service';

describe('Service Tests', () => {
  describe('Clog Service', () => {
    let service: ClogService;
    let httpMock: HttpTestingController;
    let elemDefault: IClog;
    let expectedResult: IClog | IClog[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ClogService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        empn: 'AAAAAAA',
        begindate: currentDate,
        enddate: currentDate,
        dqrq: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            begindate: currentDate.format(DATE_TIME_FORMAT),
            enddate: currentDate.format(DATE_TIME_FORMAT),
            dqrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Clog', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            begindate: currentDate.format(DATE_TIME_FORMAT),
            enddate: currentDate.format(DATE_TIME_FORMAT),
            dqrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            begindate: currentDate,
            enddate: currentDate,
            dqrq: currentDate,
          },
          returnedFromService
        );

        service.create(new Clog()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Clog', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            empn: 'BBBBBB',
            begindate: currentDate.format(DATE_TIME_FORMAT),
            enddate: currentDate.format(DATE_TIME_FORMAT),
            dqrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            begindate: currentDate,
            enddate: currentDate,
            dqrq: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Clog', () => {
        const patchObject = Object.assign(
          {
            empn: 'BBBBBB',
            enddate: currentDate.format(DATE_TIME_FORMAT),
            dqrq: currentDate.format(DATE_TIME_FORMAT),
          },
          new Clog()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            begindate: currentDate,
            enddate: currentDate,
            dqrq: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Clog', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            empn: 'BBBBBB',
            begindate: currentDate.format(DATE_TIME_FORMAT),
            enddate: currentDate.format(DATE_TIME_FORMAT),
            dqrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            begindate: currentDate,
            enddate: currentDate,
            dqrq: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Clog', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addClogToCollectionIfMissing', () => {
        it('should add a Clog to an empty array', () => {
          const clog: IClog = { id: 123 };
          expectedResult = service.addClogToCollectionIfMissing([], clog);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(clog);
        });

        it('should not add a Clog to an array that contains it', () => {
          const clog: IClog = { id: 123 };
          const clogCollection: IClog[] = [
            {
              ...clog,
            },
            { id: 456 },
          ];
          expectedResult = service.addClogToCollectionIfMissing(clogCollection, clog);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Clog to an array that doesn't contain it", () => {
          const clog: IClog = { id: 123 };
          const clogCollection: IClog[] = [{ id: 456 }];
          expectedResult = service.addClogToCollectionIfMissing(clogCollection, clog);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(clog);
        });

        it('should add only unique Clog to an array', () => {
          const clogArray: IClog[] = [{ id: 123 }, { id: 456 }, { id: 96531 }];
          const clogCollection: IClog[] = [{ id: 123 }];
          expectedResult = service.addClogToCollectionIfMissing(clogCollection, ...clogArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const clog: IClog = { id: 123 };
          const clog2: IClog = { id: 456 };
          expectedResult = service.addClogToCollectionIfMissing([], clog, clog2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(clog);
          expect(expectedResult).toContain(clog2);
        });

        it('should accept null and undefined values', () => {
          const clog: IClog = { id: 123 };
          expectedResult = service.addClogToCollectionIfMissing([], null, clog, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(clog);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
