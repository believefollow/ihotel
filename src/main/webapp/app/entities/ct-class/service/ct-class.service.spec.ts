import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICtClass, CtClass } from '../ct-class.model';

import { CtClassService } from './ct-class.service';

describe('Service Tests', () => {
  describe('CtClass Service', () => {
    let service: CtClassService;
    let httpMock: HttpTestingController;
    let elemDefault: ICtClass;
    let expectedResult: ICtClass | ICtClass[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CtClassService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        dt: currentDate,
        empn: 'AAAAAAA',
        flag: 0,
        jbempn: 'AAAAAAA',
        gotime: currentDate,
        xj: 0,
        zp: 0,
        sk: 0,
        hyk: 0,
        cq: 0,
        gz: 0,
        gfz: 0,
        yq: 0,
        yh: 0,
        zzh: 0,
        checkSign: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dt: currentDate.format(DATE_TIME_FORMAT),
            gotime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CtClass', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dt: currentDate.format(DATE_TIME_FORMAT),
            gotime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dt: currentDate,
            gotime: currentDate,
          },
          returnedFromService
        );

        service.create(new CtClass()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CtClass', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            dt: currentDate.format(DATE_TIME_FORMAT),
            empn: 'BBBBBB',
            flag: 1,
            jbempn: 'BBBBBB',
            gotime: currentDate.format(DATE_TIME_FORMAT),
            xj: 1,
            zp: 1,
            sk: 1,
            hyk: 1,
            cq: 1,
            gz: 1,
            gfz: 1,
            yq: 1,
            yh: 1,
            zzh: 1,
            checkSign: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dt: currentDate,
            gotime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CtClass', () => {
        const patchObject = Object.assign(
          {
            dt: currentDate.format(DATE_TIME_FORMAT),
            jbempn: 'BBBBBB',
            sk: 1,
            gz: 1,
            yh: 1,
            zzh: 1,
          },
          new CtClass()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dt: currentDate,
            gotime: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CtClass', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            dt: currentDate.format(DATE_TIME_FORMAT),
            empn: 'BBBBBB',
            flag: 1,
            jbempn: 'BBBBBB',
            gotime: currentDate.format(DATE_TIME_FORMAT),
            xj: 1,
            zp: 1,
            sk: 1,
            hyk: 1,
            cq: 1,
            gz: 1,
            gfz: 1,
            yq: 1,
            yh: 1,
            zzh: 1,
            checkSign: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dt: currentDate,
            gotime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CtClass', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCtClassToCollectionIfMissing', () => {
        it('should add a CtClass to an empty array', () => {
          const ctClass: ICtClass = { id: 123 };
          expectedResult = service.addCtClassToCollectionIfMissing([], ctClass);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ctClass);
        });

        it('should not add a CtClass to an array that contains it', () => {
          const ctClass: ICtClass = { id: 123 };
          const ctClassCollection: ICtClass[] = [
            {
              ...ctClass,
            },
            { id: 456 },
          ];
          expectedResult = service.addCtClassToCollectionIfMissing(ctClassCollection, ctClass);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CtClass to an array that doesn't contain it", () => {
          const ctClass: ICtClass = { id: 123 };
          const ctClassCollection: ICtClass[] = [{ id: 456 }];
          expectedResult = service.addCtClassToCollectionIfMissing(ctClassCollection, ctClass);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ctClass);
        });

        it('should add only unique CtClass to an array', () => {
          const ctClassArray: ICtClass[] = [{ id: 123 }, { id: 456 }, { id: 11799 }];
          const ctClassCollection: ICtClass[] = [{ id: 123 }];
          expectedResult = service.addCtClassToCollectionIfMissing(ctClassCollection, ...ctClassArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const ctClass: ICtClass = { id: 123 };
          const ctClass2: ICtClass = { id: 456 };
          expectedResult = service.addCtClassToCollectionIfMissing([], ctClass, ctClass2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ctClass);
          expect(expectedResult).toContain(ctClass2);
        });

        it('should accept null and undefined values', () => {
          const ctClass: ICtClass = { id: 123 };
          expectedResult = service.addCtClassToCollectionIfMissing([], null, ctClass, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ctClass);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
