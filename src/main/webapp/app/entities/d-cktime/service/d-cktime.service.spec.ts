import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDCktime, DCktime } from '../d-cktime.model';

import { DCktimeService } from './d-cktime.service';

describe('Service Tests', () => {
  describe('DCktime Service', () => {
    let service: DCktimeService;
    let httpMock: HttpTestingController;
    let elemDefault: IDCktime;
    let expectedResult: IDCktime | IDCktime[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DCktimeService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        begintime: currentDate,
        endtime: currentDate,
        depot: 'AAAAAAA',
        ckbillno: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            begintime: currentDate.format(DATE_TIME_FORMAT),
            endtime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DCktime', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            begintime: currentDate.format(DATE_TIME_FORMAT),
            endtime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            begintime: currentDate,
            endtime: currentDate,
          },
          returnedFromService
        );

        service.create(new DCktime()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DCktime', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            begintime: currentDate.format(DATE_TIME_FORMAT),
            endtime: currentDate.format(DATE_TIME_FORMAT),
            depot: 'BBBBBB',
            ckbillno: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            begintime: currentDate,
            endtime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DCktime', () => {
        const patchObject = Object.assign(
          {
            begintime: currentDate.format(DATE_TIME_FORMAT),
            ckbillno: 'BBBBBB',
          },
          new DCktime()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            begintime: currentDate,
            endtime: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DCktime', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            begintime: currentDate.format(DATE_TIME_FORMAT),
            endtime: currentDate.format(DATE_TIME_FORMAT),
            depot: 'BBBBBB',
            ckbillno: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            begintime: currentDate,
            endtime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DCktime', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDCktimeToCollectionIfMissing', () => {
        it('should add a DCktime to an empty array', () => {
          const dCktime: IDCktime = { id: 123 };
          expectedResult = service.addDCktimeToCollectionIfMissing([], dCktime);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dCktime);
        });

        it('should not add a DCktime to an array that contains it', () => {
          const dCktime: IDCktime = { id: 123 };
          const dCktimeCollection: IDCktime[] = [
            {
              ...dCktime,
            },
            { id: 456 },
          ];
          expectedResult = service.addDCktimeToCollectionIfMissing(dCktimeCollection, dCktime);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DCktime to an array that doesn't contain it", () => {
          const dCktime: IDCktime = { id: 123 };
          const dCktimeCollection: IDCktime[] = [{ id: 456 }];
          expectedResult = service.addDCktimeToCollectionIfMissing(dCktimeCollection, dCktime);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dCktime);
        });

        it('should add only unique DCktime to an array', () => {
          const dCktimeArray: IDCktime[] = [{ id: 123 }, { id: 456 }, { id: 88321 }];
          const dCktimeCollection: IDCktime[] = [{ id: 123 }];
          expectedResult = service.addDCktimeToCollectionIfMissing(dCktimeCollection, ...dCktimeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dCktime: IDCktime = { id: 123 };
          const dCktime2: IDCktime = { id: 456 };
          expectedResult = service.addDCktimeToCollectionIfMissing([], dCktime, dCktime2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dCktime);
          expect(expectedResult).toContain(dCktime2);
        });

        it('should accept null and undefined values', () => {
          const dCktime: IDCktime = { id: 123 };
          expectedResult = service.addDCktimeToCollectionIfMissing([], null, dCktime, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dCktime);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
