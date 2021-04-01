import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFwDs, FwDs } from '../fw-ds.model';

import { FwDsService } from './fw-ds.service';

describe('Service Tests', () => {
  describe('FwDs Service', () => {
    let service: FwDsService;
    let httpMock: HttpTestingController;
    let elemDefault: IFwDs;
    let expectedResult: IFwDs | IFwDs[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FwDsService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        hoteltime: currentDate,
        rq: currentDate,
        xz: 0,
        memo: 'AAAAAAA',
        fwy: 'AAAAAAA',
        roomn: 'AAAAAAA',
        rtype: 'AAAAAAA',
        empn: 'AAAAAAA',
        sl: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            rq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FwDs', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            rq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            rq: currentDate,
          },
          returnedFromService
        );

        service.create(new FwDs()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FwDs', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            rq: currentDate.format(DATE_TIME_FORMAT),
            xz: 1,
            memo: 'BBBBBB',
            fwy: 'BBBBBB',
            roomn: 'BBBBBB',
            rtype: 'BBBBBB',
            empn: 'BBBBBB',
            sl: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            rq: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a FwDs', () => {
        const patchObject = Object.assign(
          {
            xz: 1,
            memo: 'BBBBBB',
            rtype: 'BBBBBB',
            empn: 'BBBBBB',
          },
          new FwDs()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            hoteltime: currentDate,
            rq: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FwDs', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            rq: currentDate.format(DATE_TIME_FORMAT),
            xz: 1,
            memo: 'BBBBBB',
            fwy: 'BBBBBB',
            roomn: 'BBBBBB',
            rtype: 'BBBBBB',
            empn: 'BBBBBB',
            sl: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
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

      it('should delete a FwDs', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFwDsToCollectionIfMissing', () => {
        it('should add a FwDs to an empty array', () => {
          const fwDs: IFwDs = { id: 123 };
          expectedResult = service.addFwDsToCollectionIfMissing([], fwDs);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fwDs);
        });

        it('should not add a FwDs to an array that contains it', () => {
          const fwDs: IFwDs = { id: 123 };
          const fwDsCollection: IFwDs[] = [
            {
              ...fwDs,
            },
            { id: 456 },
          ];
          expectedResult = service.addFwDsToCollectionIfMissing(fwDsCollection, fwDs);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a FwDs to an array that doesn't contain it", () => {
          const fwDs: IFwDs = { id: 123 };
          const fwDsCollection: IFwDs[] = [{ id: 456 }];
          expectedResult = service.addFwDsToCollectionIfMissing(fwDsCollection, fwDs);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fwDs);
        });

        it('should add only unique FwDs to an array', () => {
          const fwDsArray: IFwDs[] = [{ id: 123 }, { id: 456 }, { id: 71730 }];
          const fwDsCollection: IFwDs[] = [{ id: 123 }];
          expectedResult = service.addFwDsToCollectionIfMissing(fwDsCollection, ...fwDsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const fwDs: IFwDs = { id: 123 };
          const fwDs2: IFwDs = { id: 456 };
          expectedResult = service.addFwDsToCollectionIfMissing([], fwDs, fwDs2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fwDs);
          expect(expectedResult).toContain(fwDs2);
        });

        it('should accept null and undefined values', () => {
          const fwDs: IFwDs = { id: 123 };
          expectedResult = service.addFwDsToCollectionIfMissing([], null, fwDs, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fwDs);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
