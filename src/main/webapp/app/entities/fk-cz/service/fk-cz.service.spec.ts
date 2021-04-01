import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFkCz, FkCz } from '../fk-cz.model';

import { FkCzService } from './fk-cz.service';

describe('Service Tests', () => {
  describe('FkCz Service', () => {
    let service: FkCzService;
    let httpMock: HttpTestingController;
    let elemDefault: IFkCz;
    let expectedResult: IFkCz | IFkCz[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FkCzService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        hoteltime: currentDate,
        wxf: 0,
        ksf: 0,
        kf: 0,
        zfs: 0,
        groupyd: 0,
        skyd: 0,
        ydwd: 0,
        qxyd: 0,
        isnew: 0,
        hoteldm: 'AAAAAAA',
        hys: 0,
        khys: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FkCz', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
          },
          returnedFromService
        );

        service.create(new FkCz()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FkCz', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            wxf: 1,
            ksf: 1,
            kf: 1,
            zfs: 1,
            groupyd: 1,
            skyd: 1,
            ydwd: 1,
            qxyd: 1,
            isnew: 1,
            hoteldm: 'BBBBBB',
            hys: 1,
            khys: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a FkCz', () => {
        const patchObject = Object.assign(
          {
            wxf: 1,
            ksf: 1,
            kf: 1,
            zfs: 1,
            skyd: 1,
            ydwd: 1,
            isnew: 1,
            hys: 1,
            khys: 1,
          },
          new FkCz()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            hoteltime: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FkCz', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            hoteltime: currentDate.format(DATE_TIME_FORMAT),
            wxf: 1,
            ksf: 1,
            kf: 1,
            zfs: 1,
            groupyd: 1,
            skyd: 1,
            ydwd: 1,
            qxyd: 1,
            isnew: 1,
            hoteldm: 'BBBBBB',
            hys: 1,
            khys: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hoteltime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FkCz', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFkCzToCollectionIfMissing', () => {
        it('should add a FkCz to an empty array', () => {
          const fkCz: IFkCz = { id: 123 };
          expectedResult = service.addFkCzToCollectionIfMissing([], fkCz);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fkCz);
        });

        it('should not add a FkCz to an array that contains it', () => {
          const fkCz: IFkCz = { id: 123 };
          const fkCzCollection: IFkCz[] = [
            {
              ...fkCz,
            },
            { id: 456 },
          ];
          expectedResult = service.addFkCzToCollectionIfMissing(fkCzCollection, fkCz);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a FkCz to an array that doesn't contain it", () => {
          const fkCz: IFkCz = { id: 123 };
          const fkCzCollection: IFkCz[] = [{ id: 456 }];
          expectedResult = service.addFkCzToCollectionIfMissing(fkCzCollection, fkCz);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fkCz);
        });

        it('should add only unique FkCz to an array', () => {
          const fkCzArray: IFkCz[] = [{ id: 123 }, { id: 456 }, { id: 84223 }];
          const fkCzCollection: IFkCz[] = [{ id: 123 }];
          expectedResult = service.addFkCzToCollectionIfMissing(fkCzCollection, ...fkCzArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const fkCz: IFkCz = { id: 123 };
          const fkCz2: IFkCz = { id: 456 };
          expectedResult = service.addFkCzToCollectionIfMissing([], fkCz, fkCz2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fkCz);
          expect(expectedResult).toContain(fkCz2);
        });

        it('should accept null and undefined values', () => {
          const fkCz: IFkCz = { id: 123 };
          expectedResult = service.addFkCzToCollectionIfMissing([], null, fkCz, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fkCz);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
