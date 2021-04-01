import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDGoods, DGoods } from '../d-goods.model';

import { DGoodsService } from './d-goods.service';

describe('Service Tests', () => {
  describe('DGoods Service', () => {
    let service: DGoodsService;
    let httpMock: HttpTestingController;
    let elemDefault: IDGoods;
    let expectedResult: IDGoods | IDGoods[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DGoodsService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        typeid: 0,
        goodsname: 'AAAAAAA',
        goodsid: 'AAAAAAA',
        ggxh: 'AAAAAAA',
        pysj: 'AAAAAAA',
        wbsj: 'AAAAAAA',
        unit: 'AAAAAAA',
        gcsl: 0,
        dcsl: 0,
        remark: 'AAAAAAA',
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

      it('should create a DGoods', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DGoods()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DGoods', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeid: 1,
            goodsname: 'BBBBBB',
            goodsid: 'BBBBBB',
            ggxh: 'BBBBBB',
            pysj: 'BBBBBB',
            wbsj: 'BBBBBB',
            unit: 'BBBBBB',
            gcsl: 1,
            dcsl: 1,
            remark: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DGoods', () => {
        const patchObject = Object.assign(
          {
            goodsname: 'BBBBBB',
            goodsid: 'BBBBBB',
            ggxh: 'BBBBBB',
            unit: 'BBBBBB',
            dcsl: 1,
            remark: 'BBBBBB',
          },
          new DGoods()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DGoods', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeid: 1,
            goodsname: 'BBBBBB',
            goodsid: 'BBBBBB',
            ggxh: 'BBBBBB',
            pysj: 'BBBBBB',
            wbsj: 'BBBBBB',
            unit: 'BBBBBB',
            gcsl: 1,
            dcsl: 1,
            remark: 'BBBBBB',
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

      it('should delete a DGoods', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDGoodsToCollectionIfMissing', () => {
        it('should add a DGoods to an empty array', () => {
          const dGoods: IDGoods = { id: 123 };
          expectedResult = service.addDGoodsToCollectionIfMissing([], dGoods);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dGoods);
        });

        it('should not add a DGoods to an array that contains it', () => {
          const dGoods: IDGoods = { id: 123 };
          const dGoodsCollection: IDGoods[] = [
            {
              ...dGoods,
            },
            { id: 456 },
          ];
          expectedResult = service.addDGoodsToCollectionIfMissing(dGoodsCollection, dGoods);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DGoods to an array that doesn't contain it", () => {
          const dGoods: IDGoods = { id: 123 };
          const dGoodsCollection: IDGoods[] = [{ id: 456 }];
          expectedResult = service.addDGoodsToCollectionIfMissing(dGoodsCollection, dGoods);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dGoods);
        });

        it('should add only unique DGoods to an array', () => {
          const dGoodsArray: IDGoods[] = [{ id: 123 }, { id: 456 }, { id: 92044 }];
          const dGoodsCollection: IDGoods[] = [{ id: 123 }];
          expectedResult = service.addDGoodsToCollectionIfMissing(dGoodsCollection, ...dGoodsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dGoods: IDGoods = { id: 123 };
          const dGoods2: IDGoods = { id: 456 };
          expectedResult = service.addDGoodsToCollectionIfMissing([], dGoods, dGoods2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dGoods);
          expect(expectedResult).toContain(dGoods2);
        });

        it('should accept null and undefined values', () => {
          const dGoods: IDGoods = { id: 123 };
          expectedResult = service.addDGoodsToCollectionIfMissing([], null, dGoods, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dGoods);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
