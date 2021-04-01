import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IErrlog, Errlog } from '../errlog.model';

import { ErrlogService } from './errlog.service';

describe('Service Tests', () => {
  describe('Errlog Service', () => {
    let service: ErrlogService;
    let httpMock: HttpTestingController;
    let elemDefault: IErrlog;
    let expectedResult: IErrlog | IErrlog[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ErrlogService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        iderrlog: 0,
        errnumber: 0,
        errtext: 'AAAAAAA',
        errwindowmenu: 'AAAAAAA',
        errobject: 'AAAAAAA',
        errevent: 'AAAAAAA',
        errline: 0,
        errtime: currentDate,
        sumbitsign: false,
        bmpfile: 'AAAAAAA',
        bmpblobContentType: 'image/png',
        bmpblob: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            errtime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Errlog', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            errtime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            errtime: currentDate,
          },
          returnedFromService
        );

        service.create(new Errlog()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Errlog', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            iderrlog: 1,
            errnumber: 1,
            errtext: 'BBBBBB',
            errwindowmenu: 'BBBBBB',
            errobject: 'BBBBBB',
            errevent: 'BBBBBB',
            errline: 1,
            errtime: currentDate.format(DATE_TIME_FORMAT),
            sumbitsign: true,
            bmpfile: 'BBBBBB',
            bmpblob: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            errtime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Errlog', () => {
        const patchObject = Object.assign(
          {
            iderrlog: 1,
            errtext: 'BBBBBB',
            errwindowmenu: 'BBBBBB',
            errline: 1,
            bmpblob: 'BBBBBB',
          },
          new Errlog()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            errtime: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Errlog', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            iderrlog: 1,
            errnumber: 1,
            errtext: 'BBBBBB',
            errwindowmenu: 'BBBBBB',
            errobject: 'BBBBBB',
            errevent: 'BBBBBB',
            errline: 1,
            errtime: currentDate.format(DATE_TIME_FORMAT),
            sumbitsign: true,
            bmpfile: 'BBBBBB',
            bmpblob: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            errtime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Errlog', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addErrlogToCollectionIfMissing', () => {
        it('should add a Errlog to an empty array', () => {
          const errlog: IErrlog = { id: 123 };
          expectedResult = service.addErrlogToCollectionIfMissing([], errlog);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(errlog);
        });

        it('should not add a Errlog to an array that contains it', () => {
          const errlog: IErrlog = { id: 123 };
          const errlogCollection: IErrlog[] = [
            {
              ...errlog,
            },
            { id: 456 },
          ];
          expectedResult = service.addErrlogToCollectionIfMissing(errlogCollection, errlog);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Errlog to an array that doesn't contain it", () => {
          const errlog: IErrlog = { id: 123 };
          const errlogCollection: IErrlog[] = [{ id: 456 }];
          expectedResult = service.addErrlogToCollectionIfMissing(errlogCollection, errlog);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(errlog);
        });

        it('should add only unique Errlog to an array', () => {
          const errlogArray: IErrlog[] = [{ id: 123 }, { id: 456 }, { id: 31871 }];
          const errlogCollection: IErrlog[] = [{ id: 123 }];
          expectedResult = service.addErrlogToCollectionIfMissing(errlogCollection, ...errlogArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const errlog: IErrlog = { id: 123 };
          const errlog2: IErrlog = { id: 456 };
          expectedResult = service.addErrlogToCollectionIfMissing([], errlog, errlog2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(errlog);
          expect(expectedResult).toContain(errlog2);
        });

        it('should accept null and undefined values', () => {
          const errlog: IErrlog = { id: 123 };
          expectedResult = service.addErrlogToCollectionIfMissing([], null, errlog, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(errlog);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
