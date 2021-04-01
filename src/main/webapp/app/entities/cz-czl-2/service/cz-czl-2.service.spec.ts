import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICzCzl2, CzCzl2 } from '../cz-czl-2.model';

import { CzCzl2Service } from './cz-czl-2.service';

describe('Service Tests', () => {
  describe('CzCzl2 Service', () => {
    let service: CzCzl2Service;
    let httpMock: HttpTestingController;
    let elemDefault: ICzCzl2;
    let expectedResult: ICzCzl2 | ICzCzl2[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CzCzl2Service);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        dr: currentDate,
        type: 'AAAAAAA',
        fs: 0,
        kfl: 0,
        fzsr: 0,
        pjz: 0,
        fsM: 0,
        kflM: 0,
        fzsrM: 0,
        pjzM: 0,
        fsY: 0,
        kflY: 0,
        fzsrY: 0,
        pjzY: 0,
        fsQ: 0,
        kflQ: 0,
        fzsrQ: 0,
        pjzQ: 0,
        dateY: 'AAAAAAA',
        dqdate: currentDate,
        empn: 'AAAAAAA',
        number: 0,
        numberM: 0,
        numberY: 0,
        hoteldm: 'AAAAAAA',
        isnew: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dr: currentDate.format(DATE_TIME_FORMAT),
            dqdate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CzCzl2', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dr: currentDate.format(DATE_TIME_FORMAT),
            dqdate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dr: currentDate,
            dqdate: currentDate,
          },
          returnedFromService
        );

        service.create(new CzCzl2()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CzCzl2', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            dr: currentDate.format(DATE_TIME_FORMAT),
            type: 'BBBBBB',
            fs: 1,
            kfl: 1,
            fzsr: 1,
            pjz: 1,
            fsM: 1,
            kflM: 1,
            fzsrM: 1,
            pjzM: 1,
            fsY: 1,
            kflY: 1,
            fzsrY: 1,
            pjzY: 1,
            fsQ: 1,
            kflQ: 1,
            fzsrQ: 1,
            pjzQ: 1,
            dateY: 'BBBBBB',
            dqdate: currentDate.format(DATE_TIME_FORMAT),
            empn: 'BBBBBB',
            number: 1,
            numberM: 1,
            numberY: 1,
            hoteldm: 'BBBBBB',
            isnew: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dr: currentDate,
            dqdate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CzCzl2', () => {
        const patchObject = Object.assign(
          {
            dr: currentDate.format(DATE_TIME_FORMAT),
            fs: 1,
            pjz: 1,
            fsM: 1,
            fzsrM: 1,
            fzsrY: 1,
            pjzY: 1,
            fsQ: 1,
            fzsrQ: 1,
            pjzQ: 1,
            dqdate: currentDate.format(DATE_TIME_FORMAT),
            number: 1,
            numberM: 1,
            isnew: 1,
          },
          new CzCzl2()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dr: currentDate,
            dqdate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CzCzl2', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            dr: currentDate.format(DATE_TIME_FORMAT),
            type: 'BBBBBB',
            fs: 1,
            kfl: 1,
            fzsr: 1,
            pjz: 1,
            fsM: 1,
            kflM: 1,
            fzsrM: 1,
            pjzM: 1,
            fsY: 1,
            kflY: 1,
            fzsrY: 1,
            pjzY: 1,
            fsQ: 1,
            kflQ: 1,
            fzsrQ: 1,
            pjzQ: 1,
            dateY: 'BBBBBB',
            dqdate: currentDate.format(DATE_TIME_FORMAT),
            empn: 'BBBBBB',
            number: 1,
            numberM: 1,
            numberY: 1,
            hoteldm: 'BBBBBB',
            isnew: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dr: currentDate,
            dqdate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CzCzl2', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCzCzl2ToCollectionIfMissing', () => {
        it('should add a CzCzl2 to an empty array', () => {
          const czCzl2: ICzCzl2 = { id: 123 };
          expectedResult = service.addCzCzl2ToCollectionIfMissing([], czCzl2);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(czCzl2);
        });

        it('should not add a CzCzl2 to an array that contains it', () => {
          const czCzl2: ICzCzl2 = { id: 123 };
          const czCzl2Collection: ICzCzl2[] = [
            {
              ...czCzl2,
            },
            { id: 456 },
          ];
          expectedResult = service.addCzCzl2ToCollectionIfMissing(czCzl2Collection, czCzl2);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CzCzl2 to an array that doesn't contain it", () => {
          const czCzl2: ICzCzl2 = { id: 123 };
          const czCzl2Collection: ICzCzl2[] = [{ id: 456 }];
          expectedResult = service.addCzCzl2ToCollectionIfMissing(czCzl2Collection, czCzl2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(czCzl2);
        });

        it('should add only unique CzCzl2 to an array', () => {
          const czCzl2Array: ICzCzl2[] = [{ id: 123 }, { id: 456 }, { id: 156 }];
          const czCzl2Collection: ICzCzl2[] = [{ id: 123 }];
          expectedResult = service.addCzCzl2ToCollectionIfMissing(czCzl2Collection, ...czCzl2Array);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const czCzl2: ICzCzl2 = { id: 123 };
          const czCzl22: ICzCzl2 = { id: 456 };
          expectedResult = service.addCzCzl2ToCollectionIfMissing([], czCzl2, czCzl22);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(czCzl2);
          expect(expectedResult).toContain(czCzl22);
        });

        it('should accept null and undefined values', () => {
          const czCzl2: ICzCzl2 = { id: 123 };
          expectedResult = service.addCzCzl2ToCollectionIfMissing([], null, czCzl2, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(czCzl2);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
