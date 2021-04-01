import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IClassRename, ClassRename } from '../class-rename.model';

import { ClassRenameService } from './class-rename.service';

describe('Service Tests', () => {
  describe('ClassRename Service', () => {
    let service: ClassRenameService;
    let httpMock: HttpTestingController;
    let elemDefault: IClassRename;
    let expectedResult: IClassRename | IClassRename[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ClassRenameService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        dt: currentDate,
        empn: 'AAAAAAA',
        oldmoney: 0,
        getmoney: 0,
        toup: 0,
        downempn: 'AAAAAAA',
        todown: 0,
        flag: 0,
        old2: 0,
        get2: 0,
        toup2: 0,
        todown2: 0,
        upempn2: 'AAAAAAA',
        im9008: 0,
        im9009: 0,
        co9991: 0,
        co9992: 0,
        co9993: 0,
        co9994: 0,
        co9995: 0,
        co9998: 0,
        im9007: 0,
        gotime: currentDate,
        co9999: 0,
        cm9008: 0,
        cm9009: 0,
        co99910: 0,
        checkSign: 'AAAAAAA',
        classPb: 'AAAAAAA',
        ck: 0,
        dk: 0,
        sjrq: currentDate,
        qsjrq: currentDate,
        byje: 0,
        xfcw: 'AAAAAAA',
        hoteldm: 'AAAAAAA',
        isnew: 0,
        co99912: 0,
        xj: 0,
        classname: 'AAAAAAA',
        co9010: 0,
        co9012: 0,
        co9013: 0,
        co9014: 0,
        co99915: 0,
        hyxj: 0,
        hysk: 0,
        hyqt: 0,
        hkxj: 0,
        hksk: 0,
        hkqt: 0,
        qtwt: 0,
        qtysq: 0,
        bbysj: 0,
        zfbje: 0,
        wxje: 0,
        w99920: 0,
        z99921: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dt: currentDate.format(DATE_TIME_FORMAT),
            gotime: currentDate.format(DATE_TIME_FORMAT),
            sjrq: currentDate.format(DATE_TIME_FORMAT),
            qsjrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ClassRename', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dt: currentDate.format(DATE_TIME_FORMAT),
            gotime: currentDate.format(DATE_TIME_FORMAT),
            sjrq: currentDate.format(DATE_TIME_FORMAT),
            qsjrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dt: currentDate,
            gotime: currentDate,
            sjrq: currentDate,
            qsjrq: currentDate,
          },
          returnedFromService
        );

        service.create(new ClassRename()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ClassRename', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            dt: currentDate.format(DATE_TIME_FORMAT),
            empn: 'BBBBBB',
            oldmoney: 1,
            getmoney: 1,
            toup: 1,
            downempn: 'BBBBBB',
            todown: 1,
            flag: 1,
            old2: 1,
            get2: 1,
            toup2: 1,
            todown2: 1,
            upempn2: 'BBBBBB',
            im9008: 1,
            im9009: 1,
            co9991: 1,
            co9992: 1,
            co9993: 1,
            co9994: 1,
            co9995: 1,
            co9998: 1,
            im9007: 1,
            gotime: currentDate.format(DATE_TIME_FORMAT),
            co9999: 1,
            cm9008: 1,
            cm9009: 1,
            co99910: 1,
            checkSign: 'BBBBBB',
            classPb: 'BBBBBB',
            ck: 1,
            dk: 1,
            sjrq: currentDate.format(DATE_TIME_FORMAT),
            qsjrq: currentDate.format(DATE_TIME_FORMAT),
            byje: 1,
            xfcw: 'BBBBBB',
            hoteldm: 'BBBBBB',
            isnew: 1,
            co99912: 1,
            xj: 1,
            classname: 'BBBBBB',
            co9010: 1,
            co9012: 1,
            co9013: 1,
            co9014: 1,
            co99915: 1,
            hyxj: 1,
            hysk: 1,
            hyqt: 1,
            hkxj: 1,
            hksk: 1,
            hkqt: 1,
            qtwt: 1,
            qtysq: 1,
            bbysj: 1,
            zfbje: 1,
            wxje: 1,
            w99920: 1,
            z99921: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dt: currentDate,
            gotime: currentDate,
            sjrq: currentDate,
            qsjrq: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ClassRename', () => {
        const patchObject = Object.assign(
          {
            dt: currentDate.format(DATE_TIME_FORMAT),
            flag: 1,
            get2: 1,
            todown2: 1,
            im9008: 1,
            co9993: 1,
            co9994: 1,
            co9995: 1,
            co9998: 1,
            co9999: 1,
            checkSign: 'BBBBBB',
            ck: 1,
            dk: 1,
            isnew: 1,
            co99912: 1,
            co9010: 1,
            co9012: 1,
            co9014: 1,
            co99915: 1,
            hyxj: 1,
            hyqt: 1,
            hksk: 1,
            qtysq: 1,
            zfbje: 1,
            w99920: 1,
            z99921: 1,
          },
          new ClassRename()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dt: currentDate,
            gotime: currentDate,
            sjrq: currentDate,
            qsjrq: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ClassRename', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            dt: currentDate.format(DATE_TIME_FORMAT),
            empn: 'BBBBBB',
            oldmoney: 1,
            getmoney: 1,
            toup: 1,
            downempn: 'BBBBBB',
            todown: 1,
            flag: 1,
            old2: 1,
            get2: 1,
            toup2: 1,
            todown2: 1,
            upempn2: 'BBBBBB',
            im9008: 1,
            im9009: 1,
            co9991: 1,
            co9992: 1,
            co9993: 1,
            co9994: 1,
            co9995: 1,
            co9998: 1,
            im9007: 1,
            gotime: currentDate.format(DATE_TIME_FORMAT),
            co9999: 1,
            cm9008: 1,
            cm9009: 1,
            co99910: 1,
            checkSign: 'BBBBBB',
            classPb: 'BBBBBB',
            ck: 1,
            dk: 1,
            sjrq: currentDate.format(DATE_TIME_FORMAT),
            qsjrq: currentDate.format(DATE_TIME_FORMAT),
            byje: 1,
            xfcw: 'BBBBBB',
            hoteldm: 'BBBBBB',
            isnew: 1,
            co99912: 1,
            xj: 1,
            classname: 'BBBBBB',
            co9010: 1,
            co9012: 1,
            co9013: 1,
            co9014: 1,
            co99915: 1,
            hyxj: 1,
            hysk: 1,
            hyqt: 1,
            hkxj: 1,
            hksk: 1,
            hkqt: 1,
            qtwt: 1,
            qtysq: 1,
            bbysj: 1,
            zfbje: 1,
            wxje: 1,
            w99920: 1,
            z99921: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dt: currentDate,
            gotime: currentDate,
            sjrq: currentDate,
            qsjrq: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ClassRename', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addClassRenameToCollectionIfMissing', () => {
        it('should add a ClassRename to an empty array', () => {
          const classRename: IClassRename = { id: 123 };
          expectedResult = service.addClassRenameToCollectionIfMissing([], classRename);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(classRename);
        });

        it('should not add a ClassRename to an array that contains it', () => {
          const classRename: IClassRename = { id: 123 };
          const classRenameCollection: IClassRename[] = [
            {
              ...classRename,
            },
            { id: 456 },
          ];
          expectedResult = service.addClassRenameToCollectionIfMissing(classRenameCollection, classRename);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ClassRename to an array that doesn't contain it", () => {
          const classRename: IClassRename = { id: 123 };
          const classRenameCollection: IClassRename[] = [{ id: 456 }];
          expectedResult = service.addClassRenameToCollectionIfMissing(classRenameCollection, classRename);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(classRename);
        });

        it('should add only unique ClassRename to an array', () => {
          const classRenameArray: IClassRename[] = [{ id: 123 }, { id: 456 }, { id: 84251 }];
          const classRenameCollection: IClassRename[] = [{ id: 123 }];
          expectedResult = service.addClassRenameToCollectionIfMissing(classRenameCollection, ...classRenameArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const classRename: IClassRename = { id: 123 };
          const classRename2: IClassRename = { id: 456 };
          expectedResult = service.addClassRenameToCollectionIfMissing([], classRename, classRename2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(classRename);
          expect(expectedResult).toContain(classRename2);
        });

        it('should accept null and undefined values', () => {
          const classRename: IClassRename = { id: 123 };
          expectedResult = service.addClassRenameToCollectionIfMissing([], null, classRename, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(classRename);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
