import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFeetype, Feetype } from '../feetype.model';

import { FeetypeService } from './feetype.service';

describe('Service Tests', () => {
  describe('Feetype Service', () => {
    let service: FeetypeService;
    let httpMock: HttpTestingController;
    let elemDefault: IFeetype;
    let expectedResult: IFeetype | IFeetype[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FeetypeService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        feenum: 0,
        feename: 'AAAAAAA',
        price: 0,
        sign: 0,
        beizhu: 'AAAAAAA',
        pym: 'AAAAAAA',
        salespotn: 0,
        depot: 'AAAAAAA',
        cbsign: 0,
        ordersign: 0,
        hoteldm: 'AAAAAAA',
        isnew: 0,
        ygj: 0,
        autosign: 'AAAAAAA',
        jj: 0,
        hyj: 0,
        dqkc: 0,
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

      it('should create a Feetype', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Feetype()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Feetype', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            feenum: 1,
            feename: 'BBBBBB',
            price: 1,
            sign: 1,
            beizhu: 'BBBBBB',
            pym: 'BBBBBB',
            salespotn: 1,
            depot: 'BBBBBB',
            cbsign: 1,
            ordersign: 1,
            hoteldm: 'BBBBBB',
            isnew: 1,
            ygj: 1,
            autosign: 'BBBBBB',
            jj: 1,
            hyj: 1,
            dqkc: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Feetype', () => {
        const patchObject = Object.assign(
          {
            feenum: 1,
            beizhu: 'BBBBBB',
            pym: 'BBBBBB',
            isnew: 1,
            ygj: 1,
            autosign: 'BBBBBB',
            dqkc: 1,
          },
          new Feetype()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Feetype', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            feenum: 1,
            feename: 'BBBBBB',
            price: 1,
            sign: 1,
            beizhu: 'BBBBBB',
            pym: 'BBBBBB',
            salespotn: 1,
            depot: 'BBBBBB',
            cbsign: 1,
            ordersign: 1,
            hoteldm: 'BBBBBB',
            isnew: 1,
            ygj: 1,
            autosign: 'BBBBBB',
            jj: 1,
            hyj: 1,
            dqkc: 1,
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

      it('should delete a Feetype', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFeetypeToCollectionIfMissing', () => {
        it('should add a Feetype to an empty array', () => {
          const feetype: IFeetype = { id: 123 };
          expectedResult = service.addFeetypeToCollectionIfMissing([], feetype);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(feetype);
        });

        it('should not add a Feetype to an array that contains it', () => {
          const feetype: IFeetype = { id: 123 };
          const feetypeCollection: IFeetype[] = [
            {
              ...feetype,
            },
            { id: 456 },
          ];
          expectedResult = service.addFeetypeToCollectionIfMissing(feetypeCollection, feetype);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Feetype to an array that doesn't contain it", () => {
          const feetype: IFeetype = { id: 123 };
          const feetypeCollection: IFeetype[] = [{ id: 456 }];
          expectedResult = service.addFeetypeToCollectionIfMissing(feetypeCollection, feetype);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(feetype);
        });

        it('should add only unique Feetype to an array', () => {
          const feetypeArray: IFeetype[] = [{ id: 123 }, { id: 456 }, { id: 65102 }];
          const feetypeCollection: IFeetype[] = [{ id: 123 }];
          expectedResult = service.addFeetypeToCollectionIfMissing(feetypeCollection, ...feetypeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const feetype: IFeetype = { id: 123 };
          const feetype2: IFeetype = { id: 456 };
          expectedResult = service.addFeetypeToCollectionIfMissing([], feetype, feetype2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(feetype);
          expect(expectedResult).toContain(feetype2);
        });

        it('should accept null and undefined values', () => {
          const feetype: IFeetype = { id: 123 };
          expectedResult = service.addFeetypeToCollectionIfMissing([], null, feetype, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(feetype);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
