import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICode1, Code1 } from '../code-1.model';

import { Code1Service } from './code-1.service';

describe('Service Tests', () => {
  describe('Code1 Service', () => {
    let service: Code1Service;
    let httpMock: HttpTestingController;
    let elemDefault: ICode1;
    let expectedResult: ICode1 | ICode1[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(Code1Service);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        code1: 'AAAAAAA',
        code2: 'AAAAAAA',
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

      it('should create a Code1', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Code1()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Code1', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code1: 'BBBBBB',
            code2: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Code1', () => {
        const patchObject = Object.assign(
          {
            code1: 'BBBBBB',
          },
          new Code1()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Code1', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code1: 'BBBBBB',
            code2: 'BBBBBB',
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

      it('should delete a Code1', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCode1ToCollectionIfMissing', () => {
        it('should add a Code1 to an empty array', () => {
          const code1: ICode1 = { id: 123 };
          expectedResult = service.addCode1ToCollectionIfMissing([], code1);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(code1);
        });

        it('should not add a Code1 to an array that contains it', () => {
          const code1: ICode1 = { id: 123 };
          const code1Collection: ICode1[] = [
            {
              ...code1,
            },
            { id: 456 },
          ];
          expectedResult = service.addCode1ToCollectionIfMissing(code1Collection, code1);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Code1 to an array that doesn't contain it", () => {
          const code1: ICode1 = { id: 123 };
          const code1Collection: ICode1[] = [{ id: 456 }];
          expectedResult = service.addCode1ToCollectionIfMissing(code1Collection, code1);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(code1);
        });

        it('should add only unique Code1 to an array', () => {
          const code1Array: ICode1[] = [{ id: 123 }, { id: 456 }, { id: 60400 }];
          const code1Collection: ICode1[] = [{ id: 123 }];
          expectedResult = service.addCode1ToCollectionIfMissing(code1Collection, ...code1Array);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const code1: ICode1 = { id: 123 };
          const code12: ICode1 = { id: 456 };
          expectedResult = service.addCode1ToCollectionIfMissing([], code1, code12);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(code1);
          expect(expectedResult).toContain(code12);
        });

        it('should accept null and undefined values', () => {
          const code1: ICode1 = { id: 123 };
          expectedResult = service.addCode1ToCollectionIfMissing([], null, code1, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(code1);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
