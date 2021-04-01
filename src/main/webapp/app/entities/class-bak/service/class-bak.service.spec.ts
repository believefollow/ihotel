import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IClassBak, ClassBak } from '../class-bak.model';

import { ClassBakService } from './class-bak.service';

describe('Service Tests', () => {
  describe('ClassBak Service', () => {
    let service: ClassBakService;
    let httpMock: HttpTestingController;
    let elemDefault: IClassBak;
    let expectedResult: IClassBak | IClassBak[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ClassBakService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        empn: 'AAAAAAA',
        dt: currentDate,
        rq: currentDate,
        ghname: 'AAAAAAA',
        bak: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dt: currentDate.format(DATE_TIME_FORMAT),
            rq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ClassBak', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dt: currentDate.format(DATE_TIME_FORMAT),
            rq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dt: currentDate,
            rq: currentDate,
          },
          returnedFromService
        );

        service.create(new ClassBak()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ClassBak', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            empn: 'BBBBBB',
            dt: currentDate.format(DATE_TIME_FORMAT),
            rq: currentDate.format(DATE_TIME_FORMAT),
            ghname: 'BBBBBB',
            bak: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dt: currentDate,
            rq: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ClassBak', () => {
        const patchObject = Object.assign(
          {
            empn: 'BBBBBB',
            dt: currentDate.format(DATE_TIME_FORMAT),
            bak: 'BBBBBB',
          },
          new ClassBak()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dt: currentDate,
            rq: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ClassBak', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            empn: 'BBBBBB',
            dt: currentDate.format(DATE_TIME_FORMAT),
            rq: currentDate.format(DATE_TIME_FORMAT),
            ghname: 'BBBBBB',
            bak: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dt: currentDate,
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

      it('should delete a ClassBak', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addClassBakToCollectionIfMissing', () => {
        it('should add a ClassBak to an empty array', () => {
          const classBak: IClassBak = { id: 123 };
          expectedResult = service.addClassBakToCollectionIfMissing([], classBak);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(classBak);
        });

        it('should not add a ClassBak to an array that contains it', () => {
          const classBak: IClassBak = { id: 123 };
          const classBakCollection: IClassBak[] = [
            {
              ...classBak,
            },
            { id: 456 },
          ];
          expectedResult = service.addClassBakToCollectionIfMissing(classBakCollection, classBak);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ClassBak to an array that doesn't contain it", () => {
          const classBak: IClassBak = { id: 123 };
          const classBakCollection: IClassBak[] = [{ id: 456 }];
          expectedResult = service.addClassBakToCollectionIfMissing(classBakCollection, classBak);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(classBak);
        });

        it('should add only unique ClassBak to an array', () => {
          const classBakArray: IClassBak[] = [{ id: 123 }, { id: 456 }, { id: 72292 }];
          const classBakCollection: IClassBak[] = [{ id: 123 }];
          expectedResult = service.addClassBakToCollectionIfMissing(classBakCollection, ...classBakArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const classBak: IClassBak = { id: 123 };
          const classBak2: IClassBak = { id: 456 };
          expectedResult = service.addClassBakToCollectionIfMissing([], classBak, classBak2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(classBak);
          expect(expectedResult).toContain(classBak2);
        });

        it('should accept null and undefined values', () => {
          const classBak: IClassBak = { id: 123 };
          expectedResult = service.addClassBakToCollectionIfMissing([], null, classBak, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(classBak);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
