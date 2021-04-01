import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFtXzs, FtXzs } from '../ft-xzs.model';

import { FtXzsService } from './ft-xzs.service';

describe('Service Tests', () => {
  describe('FtXzs Service', () => {
    let service: FtXzsService;
    let httpMock: HttpTestingController;
    let elemDefault: IFtXzs;
    let expectedResult: IFtXzs | IFtXzs[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FtXzsService);
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

      it('should create a FtXzs', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new FtXzs()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FtXzs', () => {
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

      it('should partial update a FtXzs', () => {
        const patchObject = Object.assign({}, new FtXzs());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FtXzs', () => {
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

      it('should delete a FtXzs', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFtXzsToCollectionIfMissing', () => {
        it('should add a FtXzs to an empty array', () => {
          const ftXzs: IFtXzs = { id: 123 };
          expectedResult = service.addFtXzsToCollectionIfMissing([], ftXzs);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ftXzs);
        });

        it('should not add a FtXzs to an array that contains it', () => {
          const ftXzs: IFtXzs = { id: 123 };
          const ftXzsCollection: IFtXzs[] = [
            {
              ...ftXzs,
            },
            { id: 456 },
          ];
          expectedResult = service.addFtXzsToCollectionIfMissing(ftXzsCollection, ftXzs);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a FtXzs to an array that doesn't contain it", () => {
          const ftXzs: IFtXzs = { id: 123 };
          const ftXzsCollection: IFtXzs[] = [{ id: 456 }];
          expectedResult = service.addFtXzsToCollectionIfMissing(ftXzsCollection, ftXzs);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ftXzs);
        });

        it('should add only unique FtXzs to an array', () => {
          const ftXzsArray: IFtXzs[] = [{ id: 123 }, { id: 456 }, { id: 40074 }];
          const ftXzsCollection: IFtXzs[] = [{ id: 123 }];
          expectedResult = service.addFtXzsToCollectionIfMissing(ftXzsCollection, ...ftXzsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const ftXzs: IFtXzs = { id: 123 };
          const ftXzs2: IFtXzs = { id: 456 };
          expectedResult = service.addFtXzsToCollectionIfMissing([], ftXzs, ftXzs2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ftXzs);
          expect(expectedResult).toContain(ftXzs2);
        });

        it('should accept null and undefined values', () => {
          const ftXzs: IFtXzs = { id: 123 };
          expectedResult = service.addFtXzsToCollectionIfMissing([], null, ftXzs, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ftXzs);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
