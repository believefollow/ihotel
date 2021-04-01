import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDxSed, DxSed } from '../dx-sed.model';

import { DxSedService } from './dx-sed.service';

describe('Service Tests', () => {
  describe('DxSed Service', () => {
    let service: DxSedService;
    let httpMock: HttpTestingController;
    let elemDefault: IDxSed;
    let expectedResult: IDxSed | IDxSed[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DxSedService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        dxRq: currentDate,
        dxZt: 'AAAAAAA',
        fsSj: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dxRq: currentDate.format(DATE_TIME_FORMAT),
            fsSj: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DxSed', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dxRq: currentDate.format(DATE_TIME_FORMAT),
            fsSj: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dxRq: currentDate,
            fsSj: currentDate,
          },
          returnedFromService
        );

        service.create(new DxSed()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DxSed', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            dxRq: currentDate.format(DATE_TIME_FORMAT),
            dxZt: 'BBBBBB',
            fsSj: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dxRq: currentDate,
            fsSj: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DxSed', () => {
        const patchObject = Object.assign(
          {
            dxZt: 'BBBBBB',
          },
          new DxSed()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dxRq: currentDate,
            fsSj: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DxSed', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            dxRq: currentDate.format(DATE_TIME_FORMAT),
            dxZt: 'BBBBBB',
            fsSj: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dxRq: currentDate,
            fsSj: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DxSed', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDxSedToCollectionIfMissing', () => {
        it('should add a DxSed to an empty array', () => {
          const dxSed: IDxSed = { id: 123 };
          expectedResult = service.addDxSedToCollectionIfMissing([], dxSed);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dxSed);
        });

        it('should not add a DxSed to an array that contains it', () => {
          const dxSed: IDxSed = { id: 123 };
          const dxSedCollection: IDxSed[] = [
            {
              ...dxSed,
            },
            { id: 456 },
          ];
          expectedResult = service.addDxSedToCollectionIfMissing(dxSedCollection, dxSed);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DxSed to an array that doesn't contain it", () => {
          const dxSed: IDxSed = { id: 123 };
          const dxSedCollection: IDxSed[] = [{ id: 456 }];
          expectedResult = service.addDxSedToCollectionIfMissing(dxSedCollection, dxSed);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dxSed);
        });

        it('should add only unique DxSed to an array', () => {
          const dxSedArray: IDxSed[] = [{ id: 123 }, { id: 456 }, { id: 79100 }];
          const dxSedCollection: IDxSed[] = [{ id: 123 }];
          expectedResult = service.addDxSedToCollectionIfMissing(dxSedCollection, ...dxSedArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dxSed: IDxSed = { id: 123 };
          const dxSed2: IDxSed = { id: 456 };
          expectedResult = service.addDxSedToCollectionIfMissing([], dxSed, dxSed2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dxSed);
          expect(expectedResult).toContain(dxSed2);
        });

        it('should accept null and undefined values', () => {
          const dxSed: IDxSed = { id: 123 };
          expectedResult = service.addDxSedToCollectionIfMissing([], null, dxSed, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dxSed);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
