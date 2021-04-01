import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDSpcz, DSpcz } from '../d-spcz.model';

import { DSpczService } from './d-spcz.service';

describe('Service Tests', () => {
  describe('DSpcz Service', () => {
    let service: DSpczService;
    let httpMock: HttpTestingController;
    let elemDefault: IDSpcz;
    let expectedResult: IDSpcz | IDSpcz[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DSpczService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        rq: currentDate,
        czrq: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            rq: currentDate.format(DATE_TIME_FORMAT),
            czrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DSpcz', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            rq: currentDate.format(DATE_TIME_FORMAT),
            czrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            rq: currentDate,
            czrq: currentDate,
          },
          returnedFromService
        );

        service.create(new DSpcz()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DSpcz', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            rq: currentDate.format(DATE_TIME_FORMAT),
            czrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            rq: currentDate,
            czrq: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DSpcz', () => {
        const patchObject = Object.assign(
          {
            rq: currentDate.format(DATE_TIME_FORMAT),
            czrq: currentDate.format(DATE_TIME_FORMAT),
          },
          new DSpcz()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            rq: currentDate,
            czrq: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DSpcz', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            rq: currentDate.format(DATE_TIME_FORMAT),
            czrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            rq: currentDate,
            czrq: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DSpcz', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDSpczToCollectionIfMissing', () => {
        it('should add a DSpcz to an empty array', () => {
          const dSpcz: IDSpcz = { id: 123 };
          expectedResult = service.addDSpczToCollectionIfMissing([], dSpcz);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dSpcz);
        });

        it('should not add a DSpcz to an array that contains it', () => {
          const dSpcz: IDSpcz = { id: 123 };
          const dSpczCollection: IDSpcz[] = [
            {
              ...dSpcz,
            },
            { id: 456 },
          ];
          expectedResult = service.addDSpczToCollectionIfMissing(dSpczCollection, dSpcz);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DSpcz to an array that doesn't contain it", () => {
          const dSpcz: IDSpcz = { id: 123 };
          const dSpczCollection: IDSpcz[] = [{ id: 456 }];
          expectedResult = service.addDSpczToCollectionIfMissing(dSpczCollection, dSpcz);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dSpcz);
        });

        it('should add only unique DSpcz to an array', () => {
          const dSpczArray: IDSpcz[] = [{ id: 123 }, { id: 456 }, { id: 41870 }];
          const dSpczCollection: IDSpcz[] = [{ id: 123 }];
          expectedResult = service.addDSpczToCollectionIfMissing(dSpczCollection, ...dSpczArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dSpcz: IDSpcz = { id: 123 };
          const dSpcz2: IDSpcz = { id: 456 };
          expectedResult = service.addDSpczToCollectionIfMissing([], dSpcz, dSpcz2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dSpcz);
          expect(expectedResult).toContain(dSpcz2);
        });

        it('should accept null and undefined values', () => {
          const dSpcz: IDSpcz = { id: 123 };
          expectedResult = service.addDSpczToCollectionIfMissing([], null, dSpcz, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dSpcz);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
