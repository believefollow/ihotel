import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFtXz, FtXz } from '../ft-xz.model';

import { FtXzService } from './ft-xz.service';

describe('Service Tests', () => {
  describe('FtXz Service', () => {
    let service: FtXzService;
    let httpMock: HttpTestingController;
    let elemDefault: IFtXz;
    let expectedResult: IFtXz | IFtXz[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FtXzService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        roomn: 'AAAAAAA',
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

      it('should create a FtXz', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new FtXz()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FtXz', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            roomn: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a FtXz', () => {
        const patchObject = Object.assign(
          {
            roomn: 'BBBBBB',
          },
          new FtXz()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FtXz', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            roomn: 'BBBBBB',
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

      it('should delete a FtXz', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFtXzToCollectionIfMissing', () => {
        it('should add a FtXz to an empty array', () => {
          const ftXz: IFtXz = { id: 123 };
          expectedResult = service.addFtXzToCollectionIfMissing([], ftXz);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ftXz);
        });

        it('should not add a FtXz to an array that contains it', () => {
          const ftXz: IFtXz = { id: 123 };
          const ftXzCollection: IFtXz[] = [
            {
              ...ftXz,
            },
            { id: 456 },
          ];
          expectedResult = service.addFtXzToCollectionIfMissing(ftXzCollection, ftXz);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a FtXz to an array that doesn't contain it", () => {
          const ftXz: IFtXz = { id: 123 };
          const ftXzCollection: IFtXz[] = [{ id: 456 }];
          expectedResult = service.addFtXzToCollectionIfMissing(ftXzCollection, ftXz);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ftXz);
        });

        it('should add only unique FtXz to an array', () => {
          const ftXzArray: IFtXz[] = [{ id: 123 }, { id: 456 }, { id: 48879 }];
          const ftXzCollection: IFtXz[] = [{ id: 123 }];
          expectedResult = service.addFtXzToCollectionIfMissing(ftXzCollection, ...ftXzArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const ftXz: IFtXz = { id: 123 };
          const ftXz2: IFtXz = { id: 456 };
          expectedResult = service.addFtXzToCollectionIfMissing([], ftXz, ftXz2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ftXz);
          expect(expectedResult).toContain(ftXz2);
        });

        it('should accept null and undefined values', () => {
          const ftXz: IFtXz = { id: 123 };
          expectedResult = service.addFtXzToCollectionIfMissing([], null, ftXz, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ftXz);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
