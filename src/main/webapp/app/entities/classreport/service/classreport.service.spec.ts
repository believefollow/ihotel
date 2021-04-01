import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IClassreport, Classreport } from '../classreport.model';

import { ClassreportService } from './classreport.service';

describe('Service Tests', () => {
  describe('Classreport Service', () => {
    let service: ClassreportService;
    let httpMock: HttpTestingController;
    let elemDefault: IClassreport;
    let expectedResult: IClassreport | IClassreport[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ClassreportService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        empn: 'AAAAAAA',
        dt: currentDate,
        xjUp: 0,
        yfjA: 0,
        yfjD: 0,
        gz: 0,
        zz: 0,
        zzYj: 0,
        zzJs: 0,
        zzTc: 0,
        ff: 0,
        minibar: 0,
        phone: 0,
        other: 0,
        pc: 0,
        cz: 0,
        cy: 0,
        md: 0,
        huiy: 0,
        dtb: 0,
        sszx: 0,
        cyz: 0,
        hoteldm: 'AAAAAAA',
        gzxj: 0,
        isnew: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Classreport', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dt: currentDate,
          },
          returnedFromService
        );

        service.create(new Classreport()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Classreport', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            empn: 'BBBBBB',
            dt: currentDate.format(DATE_TIME_FORMAT),
            xjUp: 1,
            yfjA: 1,
            yfjD: 1,
            gz: 1,
            zz: 1,
            zzYj: 1,
            zzJs: 1,
            zzTc: 1,
            ff: 1,
            minibar: 1,
            phone: 1,
            other: 1,
            pc: 1,
            cz: 1,
            cy: 1,
            md: 1,
            huiy: 1,
            dtb: 1,
            sszx: 1,
            cyz: 1,
            hoteldm: 'BBBBBB',
            gzxj: 1,
            isnew: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Classreport', () => {
        const patchObject = Object.assign(
          {
            dt: currentDate.format(DATE_TIME_FORMAT),
            xjUp: 1,
            yfjA: 1,
            zz: 1,
            zzTc: 1,
            phone: 1,
            other: 1,
            pc: 1,
            md: 1,
            huiy: 1,
            dtb: 1,
            sszx: 1,
            cyz: 1,
            isnew: 1,
          },
          new Classreport()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dt: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Classreport', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            empn: 'BBBBBB',
            dt: currentDate.format(DATE_TIME_FORMAT),
            xjUp: 1,
            yfjA: 1,
            yfjD: 1,
            gz: 1,
            zz: 1,
            zzYj: 1,
            zzJs: 1,
            zzTc: 1,
            ff: 1,
            minibar: 1,
            phone: 1,
            other: 1,
            pc: 1,
            cz: 1,
            cy: 1,
            md: 1,
            huiy: 1,
            dtb: 1,
            sszx: 1,
            cyz: 1,
            hoteldm: 'BBBBBB',
            gzxj: 1,
            isnew: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Classreport', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addClassreportToCollectionIfMissing', () => {
        it('should add a Classreport to an empty array', () => {
          const classreport: IClassreport = { id: 123 };
          expectedResult = service.addClassreportToCollectionIfMissing([], classreport);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(classreport);
        });

        it('should not add a Classreport to an array that contains it', () => {
          const classreport: IClassreport = { id: 123 };
          const classreportCollection: IClassreport[] = [
            {
              ...classreport,
            },
            { id: 456 },
          ];
          expectedResult = service.addClassreportToCollectionIfMissing(classreportCollection, classreport);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Classreport to an array that doesn't contain it", () => {
          const classreport: IClassreport = { id: 123 };
          const classreportCollection: IClassreport[] = [{ id: 456 }];
          expectedResult = service.addClassreportToCollectionIfMissing(classreportCollection, classreport);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(classreport);
        });

        it('should add only unique Classreport to an array', () => {
          const classreportArray: IClassreport[] = [{ id: 123 }, { id: 456 }, { id: 43424 }];
          const classreportCollection: IClassreport[] = [{ id: 123 }];
          expectedResult = service.addClassreportToCollectionIfMissing(classreportCollection, ...classreportArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const classreport: IClassreport = { id: 123 };
          const classreport2: IClassreport = { id: 456 };
          expectedResult = service.addClassreportToCollectionIfMissing([], classreport, classreport2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(classreport);
          expect(expectedResult).toContain(classreport2);
        });

        it('should accept null and undefined values', () => {
          const classreport: IClassreport = { id: 123 };
          expectedResult = service.addClassreportToCollectionIfMissing([], null, classreport, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(classreport);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
