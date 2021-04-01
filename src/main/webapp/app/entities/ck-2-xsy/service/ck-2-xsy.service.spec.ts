import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICk2xsy, Ck2xsy } from '../ck-2-xsy.model';

import { Ck2xsyService } from './ck-2-xsy.service';

describe('Service Tests', () => {
  describe('Ck2xsy Service', () => {
    let service: Ck2xsyService;
    let httpMock: HttpTestingController;
    let elemDefault: ICk2xsy;
    let expectedResult: ICk2xsy | ICk2xsy[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(Ck2xsyService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        rq: currentDate,
        cpbh: 'AAAAAAA',
        sl: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            rq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Ck2xsy', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            rq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            rq: currentDate,
          },
          returnedFromService
        );

        service.create(new Ck2xsy()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Ck2xsy', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            rq: currentDate.format(DATE_TIME_FORMAT),
            cpbh: 'BBBBBB',
            sl: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            rq: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Ck2xsy', () => {
        const patchObject = Object.assign({}, new Ck2xsy());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            rq: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Ck2xsy', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            rq: currentDate.format(DATE_TIME_FORMAT),
            cpbh: 'BBBBBB',
            sl: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should delete a Ck2xsy', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCk2xsyToCollectionIfMissing', () => {
        it('should add a Ck2xsy to an empty array', () => {
          const ck2xsy: ICk2xsy = { id: 123 };
          expectedResult = service.addCk2xsyToCollectionIfMissing([], ck2xsy);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ck2xsy);
        });

        it('should not add a Ck2xsy to an array that contains it', () => {
          const ck2xsy: ICk2xsy = { id: 123 };
          const ck2xsyCollection: ICk2xsy[] = [
            {
              ...ck2xsy,
            },
            { id: 456 },
          ];
          expectedResult = service.addCk2xsyToCollectionIfMissing(ck2xsyCollection, ck2xsy);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Ck2xsy to an array that doesn't contain it", () => {
          const ck2xsy: ICk2xsy = { id: 123 };
          const ck2xsyCollection: ICk2xsy[] = [{ id: 456 }];
          expectedResult = service.addCk2xsyToCollectionIfMissing(ck2xsyCollection, ck2xsy);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ck2xsy);
        });

        it('should add only unique Ck2xsy to an array', () => {
          const ck2xsyArray: ICk2xsy[] = [{ id: 123 }, { id: 456 }, { id: 91164 }];
          const ck2xsyCollection: ICk2xsy[] = [{ id: 123 }];
          expectedResult = service.addCk2xsyToCollectionIfMissing(ck2xsyCollection, ...ck2xsyArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const ck2xsy: ICk2xsy = { id: 123 };
          const ck2xsy2: ICk2xsy = { id: 456 };
          expectedResult = service.addCk2xsyToCollectionIfMissing([], ck2xsy, ck2xsy2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ck2xsy);
          expect(expectedResult).toContain(ck2xsy2);
        });

        it('should accept null and undefined values', () => {
          const ck2xsy: ICk2xsy = { id: 123 };
          expectedResult = service.addCk2xsyToCollectionIfMissing([], null, ck2xsy, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ck2xsy);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
