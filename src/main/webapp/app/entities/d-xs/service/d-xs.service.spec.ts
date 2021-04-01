import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDXs, DXs } from '../d-xs.model';

import { DXsService } from './d-xs.service';

describe('Service Tests', () => {
  describe('DXs Service', () => {
    let service: DXsService;
    let httpMock: HttpTestingController;
    let elemDefault: IDXs;
    let expectedResult: IDXs | IDXs[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DXsService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        begintime: currentDate,
        endtime: currentDate,
        ckbillno: 'AAAAAAA',
        depot: 'AAAAAAA',
        kcid: 0,
        ckid: 0,
        spbm: 'AAAAAAA',
        spmc: 'AAAAAAA',
        unit: 'AAAAAAA',
        rkprice: 0,
        xsprice: 0,
        sl: 0,
        rkje: 0,
        xsje: 0,
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

      it('should create a DXs', () => {
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

        service.create(new DXs()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DXs', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            begintime: currentDate.format(DATE_TIME_FORMAT),
            endtime: currentDate.format(DATE_TIME_FORMAT),
            ckbillno: 'BBBBBB',
            depot: 'BBBBBB',
            kcid: 1,
            ckid: 1,
            spbm: 'BBBBBB',
            spmc: 'BBBBBB',
            unit: 'BBBBBB',
            rkprice: 1,
            xsprice: 1,
            sl: 1,
            rkje: 1,
            xsje: 1,
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

      it('should partial update a DXs', () => {
        const patchObject = Object.assign(
          {
            begintime: currentDate.format(DATE_TIME_FORMAT),
            unit: 'BBBBBB',
            xsprice: 1,
            rkje: 1,
            xsje: 1,
          },
          new DXs()
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

      it('should return a list of DXs', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            begintime: currentDate.format(DATE_TIME_FORMAT),
            endtime: currentDate.format(DATE_TIME_FORMAT),
            ckbillno: 'BBBBBB',
            depot: 'BBBBBB',
            kcid: 1,
            ckid: 1,
            spbm: 'BBBBBB',
            spmc: 'BBBBBB',
            unit: 'BBBBBB',
            rkprice: 1,
            xsprice: 1,
            sl: 1,
            rkje: 1,
            xsje: 1,
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

      it('should delete a DXs', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDXsToCollectionIfMissing', () => {
        it('should add a DXs to an empty array', () => {
          const dXs: IDXs = { id: 123 };
          expectedResult = service.addDXsToCollectionIfMissing([], dXs);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dXs);
        });

        it('should not add a DXs to an array that contains it', () => {
          const dXs: IDXs = { id: 123 };
          const dXsCollection: IDXs[] = [
            {
              ...dXs,
            },
            { id: 456 },
          ];
          expectedResult = service.addDXsToCollectionIfMissing(dXsCollection, dXs);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DXs to an array that doesn't contain it", () => {
          const dXs: IDXs = { id: 123 };
          const dXsCollection: IDXs[] = [{ id: 456 }];
          expectedResult = service.addDXsToCollectionIfMissing(dXsCollection, dXs);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dXs);
        });

        it('should add only unique DXs to an array', () => {
          const dXsArray: IDXs[] = [{ id: 123 }, { id: 456 }, { id: 73466 }];
          const dXsCollection: IDXs[] = [{ id: 123 }];
          expectedResult = service.addDXsToCollectionIfMissing(dXsCollection, ...dXsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dXs: IDXs = { id: 123 };
          const dXs2: IDXs = { id: 456 };
          expectedResult = service.addDXsToCollectionIfMissing([], dXs, dXs2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dXs);
          expect(expectedResult).toContain(dXs2);
        });

        it('should accept null and undefined values', () => {
          const dXs: IDXs = { id: 123 };
          expectedResult = service.addDXsToCollectionIfMissing([], null, dXs, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dXs);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
