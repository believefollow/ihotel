import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAuditinfo, Auditinfo } from '../auditinfo.model';

import { AuditinfoService } from './auditinfo.service';

describe('Service Tests', () => {
  describe('Auditinfo Service', () => {
    let service: AuditinfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IAuditinfo;
    let expectedResult: IAuditinfo | IAuditinfo[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AuditinfoService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        auditdate: currentDate,
        audittime: currentDate,
        empn: 'AAAAAAA',
        aidentify: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            auditdate: currentDate.format(DATE_TIME_FORMAT),
            audittime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Auditinfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            auditdate: currentDate.format(DATE_TIME_FORMAT),
            audittime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            auditdate: currentDate,
            audittime: currentDate,
          },
          returnedFromService
        );

        service.create(new Auditinfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Auditinfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            auditdate: currentDate.format(DATE_TIME_FORMAT),
            audittime: currentDate.format(DATE_TIME_FORMAT),
            empn: 'BBBBBB',
            aidentify: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            auditdate: currentDate,
            audittime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Auditinfo', () => {
        const patchObject = Object.assign(
          {
            empn: 'BBBBBB',
          },
          new Auditinfo()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            auditdate: currentDate,
            audittime: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Auditinfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            auditdate: currentDate.format(DATE_TIME_FORMAT),
            audittime: currentDate.format(DATE_TIME_FORMAT),
            empn: 'BBBBBB',
            aidentify: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            auditdate: currentDate,
            audittime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Auditinfo', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAuditinfoToCollectionIfMissing', () => {
        it('should add a Auditinfo to an empty array', () => {
          const auditinfo: IAuditinfo = { id: 123 };
          expectedResult = service.addAuditinfoToCollectionIfMissing([], auditinfo);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(auditinfo);
        });

        it('should not add a Auditinfo to an array that contains it', () => {
          const auditinfo: IAuditinfo = { id: 123 };
          const auditinfoCollection: IAuditinfo[] = [
            {
              ...auditinfo,
            },
            { id: 456 },
          ];
          expectedResult = service.addAuditinfoToCollectionIfMissing(auditinfoCollection, auditinfo);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Auditinfo to an array that doesn't contain it", () => {
          const auditinfo: IAuditinfo = { id: 123 };
          const auditinfoCollection: IAuditinfo[] = [{ id: 456 }];
          expectedResult = service.addAuditinfoToCollectionIfMissing(auditinfoCollection, auditinfo);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(auditinfo);
        });

        it('should add only unique Auditinfo to an array', () => {
          const auditinfoArray: IAuditinfo[] = [{ id: 123 }, { id: 456 }, { id: 4941 }];
          const auditinfoCollection: IAuditinfo[] = [{ id: 123 }];
          expectedResult = service.addAuditinfoToCollectionIfMissing(auditinfoCollection, ...auditinfoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const auditinfo: IAuditinfo = { id: 123 };
          const auditinfo2: IAuditinfo = { id: 456 };
          expectedResult = service.addAuditinfoToCollectionIfMissing([], auditinfo, auditinfo2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(auditinfo);
          expect(expectedResult).toContain(auditinfo2);
        });

        it('should accept null and undefined values', () => {
          const auditinfo: IAuditinfo = { id: 123 };
          expectedResult = service.addAuditinfoToCollectionIfMissing([], null, auditinfo, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(auditinfo);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
