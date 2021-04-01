import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFwYlwp, FwYlwp } from '../fw-ylwp.model';

import { FwYlwpService } from './fw-ylwp.service';

describe('Service Tests', () => {
  describe('FwYlwp Service', () => {
    let service: FwYlwpService;
    let httpMock: HttpTestingController;
    let elemDefault: IFwYlwp;
    let expectedResult: IFwYlwp | IFwYlwp[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FwYlwpService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        roomn: 'AAAAAAA',
        guestname: 'AAAAAAA',
        memo: 'AAAAAAA',
        sdr: 'AAAAAAA',
        sdrq: currentDate,
        rlr: 'AAAAAAA',
        rlrq: currentDate,
        remark: 'AAAAAAA',
        empn: 'AAAAAAA',
        czrq: currentDate,
        flag: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            sdrq: currentDate.format(DATE_TIME_FORMAT),
            rlrq: currentDate.format(DATE_TIME_FORMAT),
            czrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FwYlwp', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            sdrq: currentDate.format(DATE_TIME_FORMAT),
            rlrq: currentDate.format(DATE_TIME_FORMAT),
            czrq: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sdrq: currentDate,
            rlrq: currentDate,
            czrq: currentDate,
          },
          returnedFromService
        );

        service.create(new FwYlwp()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FwYlwp', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            roomn: 'BBBBBB',
            guestname: 'BBBBBB',
            memo: 'BBBBBB',
            sdr: 'BBBBBB',
            sdrq: currentDate.format(DATE_TIME_FORMAT),
            rlr: 'BBBBBB',
            rlrq: currentDate.format(DATE_TIME_FORMAT),
            remark: 'BBBBBB',
            empn: 'BBBBBB',
            czrq: currentDate.format(DATE_TIME_FORMAT),
            flag: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sdrq: currentDate,
            rlrq: currentDate,
            czrq: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a FwYlwp', () => {
        const patchObject = Object.assign(
          {
            roomn: 'BBBBBB',
            guestname: 'BBBBBB',
          },
          new FwYlwp()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            sdrq: currentDate,
            rlrq: currentDate,
            czrq: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FwYlwp', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            roomn: 'BBBBBB',
            guestname: 'BBBBBB',
            memo: 'BBBBBB',
            sdr: 'BBBBBB',
            sdrq: currentDate.format(DATE_TIME_FORMAT),
            rlr: 'BBBBBB',
            rlrq: currentDate.format(DATE_TIME_FORMAT),
            remark: 'BBBBBB',
            empn: 'BBBBBB',
            czrq: currentDate.format(DATE_TIME_FORMAT),
            flag: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            sdrq: currentDate,
            rlrq: currentDate,
            czrq: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FwYlwp', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFwYlwpToCollectionIfMissing', () => {
        it('should add a FwYlwp to an empty array', () => {
          const fwYlwp: IFwYlwp = { id: 123 };
          expectedResult = service.addFwYlwpToCollectionIfMissing([], fwYlwp);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fwYlwp);
        });

        it('should not add a FwYlwp to an array that contains it', () => {
          const fwYlwp: IFwYlwp = { id: 123 };
          const fwYlwpCollection: IFwYlwp[] = [
            {
              ...fwYlwp,
            },
            { id: 456 },
          ];
          expectedResult = service.addFwYlwpToCollectionIfMissing(fwYlwpCollection, fwYlwp);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a FwYlwp to an array that doesn't contain it", () => {
          const fwYlwp: IFwYlwp = { id: 123 };
          const fwYlwpCollection: IFwYlwp[] = [{ id: 456 }];
          expectedResult = service.addFwYlwpToCollectionIfMissing(fwYlwpCollection, fwYlwp);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fwYlwp);
        });

        it('should add only unique FwYlwp to an array', () => {
          const fwYlwpArray: IFwYlwp[] = [{ id: 123 }, { id: 456 }, { id: 30441 }];
          const fwYlwpCollection: IFwYlwp[] = [{ id: 123 }];
          expectedResult = service.addFwYlwpToCollectionIfMissing(fwYlwpCollection, ...fwYlwpArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const fwYlwp: IFwYlwp = { id: 123 };
          const fwYlwp2: IFwYlwp = { id: 456 };
          expectedResult = service.addFwYlwpToCollectionIfMissing([], fwYlwp, fwYlwp2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(fwYlwp);
          expect(expectedResult).toContain(fwYlwp2);
        });

        it('should accept null and undefined values', () => {
          const fwYlwp: IFwYlwp = { id: 123 };
          expectedResult = service.addFwYlwpToCollectionIfMissing([], null, fwYlwp, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(fwYlwp);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
