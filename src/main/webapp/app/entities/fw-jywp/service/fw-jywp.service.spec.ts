import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFwJywp, FwJywp } from '../fw-jywp.model';

import { FwJywpService } from './fw-jywp.service';

describe('Service Tests', () => {
  describe('FwJywp Service', () => {
    let service: FwJywpService;
    let httpMock: HttpTestingController;
    let elemDefault: IFwJywp;
    let expectedResult: IFwJywp | IFwJywp[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FwJywpService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        jyrq: currentDate,
        roomn: 'AAAAAAA',
        guestname: 'AAAAAAA',
        jywp: 'AAAAAAA',
        fwy: 'AAAAAAA',
        djr: 'AAAAAAA',
        flag: 'AAAAAAA',
        ghrq: currentDate,
        djrq: currentDate,
        remark: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            jyrq: currentDate.format(DATE_TIME_FORMAT),
            ghrq: currentDate.format(DATE_TIME_FORMAT),
            djrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FwJywp', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            jyrq: currentDate.format(DATE_TIME_FORMAT),
            ghrq: currentDate.format(DATE_TIME_FORMAT),
            djrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            jyrq: currentDate,
            ghrq: currentDate,
            djrq: currentDate,
          },
          returnedFromService
        );

        service.create(new FwJywp()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FwJywp', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            jyrq: currentDate.format(DATE_TIME_FORMAT),
            roomn: 'BBBBBB',
            guestname: 'BBBBBB',
            jywp: 'BBBBBB',
            fwy: 'BBBBBB',
            djr: 'BBBBBB',
            flag: 'BBBBBB',
            ghrq: currentDate.format(DATE_TIME_FORMAT),
            djrq: currentDate.format(DATE_TIME_FORMAT),
            remark: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            jyrq: currentDate,
            ghrq: currentDate,
            djrq: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a FwJywp', () => {
        const patchObject = Object.assign(
          {
            jyrq: currentDate.format(DATE_TIME_FORMAT),
            roomn: 'BBBBBB',
            fwy: 'BBBBBB',
            djr: 'BBBBBB',
            flag: 'BBBBBB',
            djrq: currentDate.format(DATE_TIME_FORMAT),
          },
          new FwJywp()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            jyrq: currentDate,
            ghrq: currentDate,
            djrq: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FwJywp', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            jyrq: currentDate.format(DATE_TIME_FORMAT),
            roomn: 'BBBBBB',
            guestname: 'BBBBBB',
            jywp: 'BBBBBB',
            fwy: 'BBBBBB',
            djr: 'BBBBBB',
            flag: 'BBBBBB',
            ghrq: currentDate.format(DATE_TIME_FORMAT),
            djrq: currentDate.format(DATE_TIME_FORMAT),
            remark: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            jyrq: currentDate,
            ghrq: currentDate,
            djrq: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FwJywp', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFwJywpToCollectionIfMissing', () => {
        it('should add a FwJywp to an empty array', () => {
          const fwJywp: IFwJywp = { id: 123 };
          expectedResult = service.addFwJywpToCollectionIfMissing([], fwJywp);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fwJywp);
        });

        it('should not add a FwJywp to an array that contains it', () => {
          const fwJywp: IFwJywp = { id: 123 };
          const fwJywpCollection: IFwJywp[] = [
            {
              ...fwJywp,
            },
            { id: 456 },
          ];
          expectedResult = service.addFwJywpToCollectionIfMissing(fwJywpCollection, fwJywp);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a FwJywp to an array that doesn't contain it", () => {
          const fwJywp: IFwJywp = { id: 123 };
          const fwJywpCollection: IFwJywp[] = [{ id: 456 }];
          expectedResult = service.addFwJywpToCollectionIfMissing(fwJywpCollection, fwJywp);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fwJywp);
        });

        it('should add only unique FwJywp to an array', () => {
          const fwJywpArray: IFwJywp[] = [{ id: 123 }, { id: 456 }, { id: 16082 }];
          const fwJywpCollection: IFwJywp[] = [{ id: 123 }];
          expectedResult = service.addFwJywpToCollectionIfMissing(fwJywpCollection, ...fwJywpArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const fwJywp: IFwJywp = { id: 123 };
          const fwJywp2: IFwJywp = { id: 456 };
          expectedResult = service.addFwJywpToCollectionIfMissing([], fwJywp, fwJywp2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fwJywp);
          expect(expectedResult).toContain(fwJywp2);
        });

        it('should accept null and undefined values', () => {
          const fwJywp: IFwJywp = { id: 123 };
          expectedResult = service.addFwJywpToCollectionIfMissing([], null, fwJywp, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fwJywp);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
