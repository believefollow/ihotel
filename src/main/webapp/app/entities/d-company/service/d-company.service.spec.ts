import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDCompany, DCompany } from '../d-company.model';

import { DCompanyService } from './d-company.service';

describe('Service Tests', () => {
  describe('DCompany Service', () => {
    let service: DCompanyService;
    let httpMock: HttpTestingController;
    let elemDefault: IDCompany;
    let expectedResult: IDCompany | IDCompany[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DCompanyService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        company: 'AAAAAAA',
        linkman: 'AAAAAAA',
        phone: 'AAAAAAA',
        address: 'AAAAAAA',
        remark: 'AAAAAAA',
        fax: 'AAAAAAA',
        id: 0,
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

      it('should create a DCompany', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DCompany()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DCompany', () => {
        const returnedFromService = Object.assign(
          {
            company: 'BBBBBB',
            linkman: 'BBBBBB',
            phone: 'BBBBBB',
            address: 'BBBBBB',
            remark: 'BBBBBB',
            fax: 'BBBBBB',
            id: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DCompany', () => {
        const patchObject = Object.assign(
          {
            linkman: 'BBBBBB',
            address: 'BBBBBB',
            remark: 'BBBBBB',
          },
          new DCompany()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DCompany', () => {
        const returnedFromService = Object.assign(
          {
            company: 'BBBBBB',
            linkman: 'BBBBBB',
            phone: 'BBBBBB',
            address: 'BBBBBB',
            remark: 'BBBBBB',
            fax: 'BBBBBB',
            id: 1,
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

      it('should delete a DCompany', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDCompanyToCollectionIfMissing', () => {
        it('should add a DCompany to an empty array', () => {
          const dCompany: IDCompany = { id: 123 };
          expectedResult = service.addDCompanyToCollectionIfMissing([], dCompany);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dCompany);
        });

        it('should not add a DCompany to an array that contains it', () => {
          const dCompany: IDCompany = { id: 123 };
          const dCompanyCollection: IDCompany[] = [
            {
              ...dCompany,
            },
            { id: 456 },
          ];
          expectedResult = service.addDCompanyToCollectionIfMissing(dCompanyCollection, dCompany);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DCompany to an array that doesn't contain it", () => {
          const dCompany: IDCompany = { id: 123 };
          const dCompanyCollection: IDCompany[] = [{ id: 456 }];
          expectedResult = service.addDCompanyToCollectionIfMissing(dCompanyCollection, dCompany);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dCompany);
        });

        it('should add only unique DCompany to an array', () => {
          const dCompanyArray: IDCompany[] = [{ id: 123 }, { id: 456 }, { id: 85850 }];
          const dCompanyCollection: IDCompany[] = [{ id: 123 }];
          expectedResult = service.addDCompanyToCollectionIfMissing(dCompanyCollection, ...dCompanyArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dCompany: IDCompany = { id: 123 };
          const dCompany2: IDCompany = { id: 456 };
          expectedResult = service.addDCompanyToCollectionIfMissing([], dCompany, dCompany2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dCompany);
          expect(expectedResult).toContain(dCompany2);
        });

        it('should accept null and undefined values', () => {
          const dCompany: IDCompany = { id: 123 };
          expectedResult = service.addDCompanyToCollectionIfMissing([], null, dCompany, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dCompany);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
