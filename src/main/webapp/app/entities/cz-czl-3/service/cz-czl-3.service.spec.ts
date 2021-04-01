import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICzCzl3, CzCzl3 } from '../cz-czl-3.model';

import { CzCzl3Service } from './cz-czl-3.service';

describe('Service Tests', () => {
  describe('CzCzl3 Service', () => {
    let service: CzCzl3Service;
    let httpMock: HttpTestingController;
    let elemDefault: ICzCzl3;
    let expectedResult: ICzCzl3 | ICzCzl3[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CzCzl3Service);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        zfs: 0,
        kfs: 0,
        protocoln: 'AAAAAAA',
        roomtype: 'AAAAAAA',
        sl: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CzCzl3', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CzCzl3()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CzCzl3', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            zfs: 1,
            kfs: 1,
            protocoln: 'BBBBBB',
            roomtype: 'BBBBBB',
            sl: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CzCzl3', () => {
        const patchObject = Object.assign(
          {
            zfs: 1,
            kfs: 1,
          },
          new CzCzl3()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CzCzl3', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            zfs: 1,
            kfs: 1,
            protocoln: 'BBBBBB',
            roomtype: 'BBBBBB',
            sl: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CzCzl3', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCzCzl3ToCollectionIfMissing', () => {
        it('should add a CzCzl3 to an empty array', () => {
          const czCzl3: ICzCzl3 = { id: 123 };
          expectedResult = service.addCzCzl3ToCollectionIfMissing([], czCzl3);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(czCzl3);
        });

        it('should not add a CzCzl3 to an array that contains it', () => {
          const czCzl3: ICzCzl3 = { id: 123 };
          const czCzl3Collection: ICzCzl3[] = [
            {
              ...czCzl3,
            },
            { id: 456 },
          ];
          expectedResult = service.addCzCzl3ToCollectionIfMissing(czCzl3Collection, czCzl3);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CzCzl3 to an array that doesn't contain it", () => {
          const czCzl3: ICzCzl3 = { id: 123 };
          const czCzl3Collection: ICzCzl3[] = [{ id: 456 }];
          expectedResult = service.addCzCzl3ToCollectionIfMissing(czCzl3Collection, czCzl3);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(czCzl3);
        });

        it('should add only unique CzCzl3 to an array', () => {
          const czCzl3Array: ICzCzl3[] = [{ id: 123 }, { id: 456 }, { id: 3112 }];
          const czCzl3Collection: ICzCzl3[] = [{ id: 123 }];
          expectedResult = service.addCzCzl3ToCollectionIfMissing(czCzl3Collection, ...czCzl3Array);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const czCzl3: ICzCzl3 = { id: 123 };
          const czCzl32: ICzCzl3 = { id: 456 };
          expectedResult = service.addCzCzl3ToCollectionIfMissing([], czCzl3, czCzl32);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(czCzl3);
          expect(expectedResult).toContain(czCzl32);
        });

        it('should accept null and undefined values', () => {
          const czCzl3: ICzCzl3 = { id: 123 };
          expectedResult = service.addCzCzl3ToCollectionIfMissing([], null, czCzl3, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(czCzl3);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
