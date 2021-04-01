import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDDepot, DDepot } from '../d-depot.model';

import { DDepotService } from './d-depot.service';

describe('Service Tests', () => {
  describe('DDepot Service', () => {
    let service: DDepotService;
    let httpMock: HttpTestingController;
    let elemDefault: IDDepot;
    let expectedResult: IDDepot | IDDepot[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DDepotService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        depotid: false,
        depot: 'AAAAAAA',
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

      it('should create a DDepot', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DDepot()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DDepot', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            depotid: true,
            depot: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DDepot', () => {
        const patchObject = Object.assign({}, new DDepot());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DDepot', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            depotid: true,
            depot: 'BBBBBB',
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

      it('should delete a DDepot', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDDepotToCollectionIfMissing', () => {
        it('should add a DDepot to an empty array', () => {
          const dDepot: IDDepot = { id: 123 };
          expectedResult = service.addDDepotToCollectionIfMissing([], dDepot);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dDepot);
        });

        it('should not add a DDepot to an array that contains it', () => {
          const dDepot: IDDepot = { id: 123 };
          const dDepotCollection: IDDepot[] = [
            {
              ...dDepot,
            },
            { id: 456 },
          ];
          expectedResult = service.addDDepotToCollectionIfMissing(dDepotCollection, dDepot);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DDepot to an array that doesn't contain it", () => {
          const dDepot: IDDepot = { id: 123 };
          const dDepotCollection: IDDepot[] = [{ id: 456 }];
          expectedResult = service.addDDepotToCollectionIfMissing(dDepotCollection, dDepot);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dDepot);
        });

        it('should add only unique DDepot to an array', () => {
          const dDepotArray: IDDepot[] = [{ id: 123 }, { id: 456 }, { id: 84888 }];
          const dDepotCollection: IDDepot[] = [{ id: 123 }];
          expectedResult = service.addDDepotToCollectionIfMissing(dDepotCollection, ...dDepotArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dDepot: IDDepot = { id: 123 };
          const dDepot2: IDDepot = { id: 456 };
          expectedResult = service.addDDepotToCollectionIfMissing([], dDepot, dDepot2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dDepot);
          expect(expectedResult).toContain(dDepot2);
        });

        it('should accept null and undefined values', () => {
          const dDepot: IDDepot = { id: 123 };
          expectedResult = service.addDDepotToCollectionIfMissing([], null, dDepot, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dDepot);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
