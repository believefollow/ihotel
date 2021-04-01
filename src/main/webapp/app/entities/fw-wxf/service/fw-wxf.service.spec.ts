import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFwWxf, FwWxf } from '../fw-wxf.model';

import { FwWxfService } from './fw-wxf.service';

describe('Service Tests', () => {
  describe('FwWxf Service', () => {
    let service: FwWxfService;
    let httpMock: HttpTestingController;
    let elemDefault: IFwWxf;
    let expectedResult: IFwWxf | IFwWxf[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FwWxfService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        roomn: 'AAAAAAA',
        memo: 'AAAAAAA',
        djrq: currentDate,
        wxr: 'AAAAAAA',
        wcrq: currentDate,
        djr: 'AAAAAAA',
        flag: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            djrq: currentDate.format(DATE_TIME_FORMAT),
            wcrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FwWxf', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            djrq: currentDate.format(DATE_TIME_FORMAT),
            wcrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            djrq: currentDate,
            wcrq: currentDate,
          },
          returnedFromService
        );

        service.create(new FwWxf()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FwWxf', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            roomn: 'BBBBBB',
            memo: 'BBBBBB',
            djrq: currentDate.format(DATE_TIME_FORMAT),
            wxr: 'BBBBBB',
            wcrq: currentDate.format(DATE_TIME_FORMAT),
            djr: 'BBBBBB',
            flag: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            djrq: currentDate,
            wcrq: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a FwWxf', () => {
        const patchObject = Object.assign(
          {
            roomn: 'BBBBBB',
            wxr: 'BBBBBB',
            wcrq: currentDate.format(DATE_TIME_FORMAT),
            djr: 'BBBBBB',
            flag: 'BBBBBB',
          },
          new FwWxf()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            djrq: currentDate,
            wcrq: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FwWxf', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            roomn: 'BBBBBB',
            memo: 'BBBBBB',
            djrq: currentDate.format(DATE_TIME_FORMAT),
            wxr: 'BBBBBB',
            wcrq: currentDate.format(DATE_TIME_FORMAT),
            djr: 'BBBBBB',
            flag: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            djrq: currentDate,
            wcrq: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FwWxf', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFwWxfToCollectionIfMissing', () => {
        it('should add a FwWxf to an empty array', () => {
          const fwWxf: IFwWxf = { id: 123 };
          expectedResult = service.addFwWxfToCollectionIfMissing([], fwWxf);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fwWxf);
        });

        it('should not add a FwWxf to an array that contains it', () => {
          const fwWxf: IFwWxf = { id: 123 };
          const fwWxfCollection: IFwWxf[] = [
            {
              ...fwWxf,
            },
            { id: 456 },
          ];
          expectedResult = service.addFwWxfToCollectionIfMissing(fwWxfCollection, fwWxf);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a FwWxf to an array that doesn't contain it", () => {
          const fwWxf: IFwWxf = { id: 123 };
          const fwWxfCollection: IFwWxf[] = [{ id: 456 }];
          expectedResult = service.addFwWxfToCollectionIfMissing(fwWxfCollection, fwWxf);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fwWxf);
        });

        it('should add only unique FwWxf to an array', () => {
          const fwWxfArray: IFwWxf[] = [{ id: 123 }, { id: 456 }, { id: 25738 }];
          const fwWxfCollection: IFwWxf[] = [{ id: 123 }];
          expectedResult = service.addFwWxfToCollectionIfMissing(fwWxfCollection, ...fwWxfArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const fwWxf: IFwWxf = { id: 123 };
          const fwWxf2: IFwWxf = { id: 456 };
          expectedResult = service.addFwWxfToCollectionIfMissing([], fwWxf, fwWxf2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fwWxf);
          expect(expectedResult).toContain(fwWxf2);
        });

        it('should accept null and undefined values', () => {
          const fwWxf: IFwWxf = { id: 123 };
          expectedResult = service.addFwWxfToCollectionIfMissing([], null, fwWxf, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fwWxf);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
