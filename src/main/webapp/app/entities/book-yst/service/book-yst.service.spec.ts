import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBookYst, BookYst } from '../book-yst.model';

import { BookYstService } from './book-yst.service';

describe('Service Tests', () => {
  describe('BookYst Service', () => {
    let service: BookYstService;
    let httpMock: HttpTestingController;
    let elemDefault: IBookYst;
    let expectedResult: IBookYst | IBookYst[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(BookYstService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        roomcode: 'AAAAAAA',
        roomname: 'AAAAAAA',
        roomnum: 'AAAAAAA',
        roomseparatenum: 'AAAAAAA',
        bedids: 'AAAAAAA',
        bedsimpledesc: 'AAAAAAA',
        bednum: 'AAAAAAA',
        roomsize: 'AAAAAAA',
        roomfloor: 'AAAAAAA',
        netservice: 'AAAAAAA',
        nettype: 'AAAAAAA',
        iswindow: 'AAAAAAA',
        remark: 'AAAAAAA',
        sortid: 'AAAAAAA',
        roomstate: 'AAAAAAA',
        source: 'AAAAAAA',
        roomamenities: 'AAAAAAA',
        maxguestnums: 'AAAAAAA',
        roomdistribution: 'AAAAAAA',
        conditionbeforedays: 'AAAAAAA',
        conditionleastdays: 'AAAAAAA',
        conditionleastroomnum: 'AAAAAAA',
        paymentype: 'AAAAAAA',
        rateplandesc: 'AAAAAAA',
        rateplanname: 'AAAAAAA',
        rateplanstate: 'AAAAAAA',
        addvaluebednum: 'AAAAAAA',
        addvaluebedprice: 'AAAAAAA',
        addvaluebreakfastnum: 'AAAAAAA',
        addvaluebreakfastprice: 'AAAAAAA',
        baseprice: 'AAAAAAA',
        saleprice: 'AAAAAAA',
        marketprice: 'AAAAAAA',
        hotelproductservice: 'AAAAAAA',
        hotelproductservicedesc: 'AAAAAAA',
        hotelproductid: 'AAAAAAA',
        roomid: 'AAAAAAA',
        hotelid: 'AAAAAAA',
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

      it('should create a BookYst', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new BookYst()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BookYst', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            roomcode: 'BBBBBB',
            roomname: 'BBBBBB',
            roomnum: 'BBBBBB',
            roomseparatenum: 'BBBBBB',
            bedids: 'BBBBBB',
            bedsimpledesc: 'BBBBBB',
            bednum: 'BBBBBB',
            roomsize: 'BBBBBB',
            roomfloor: 'BBBBBB',
            netservice: 'BBBBBB',
            nettype: 'BBBBBB',
            iswindow: 'BBBBBB',
            remark: 'BBBBBB',
            sortid: 'BBBBBB',
            roomstate: 'BBBBBB',
            source: 'BBBBBB',
            roomamenities: 'BBBBBB',
            maxguestnums: 'BBBBBB',
            roomdistribution: 'BBBBBB',
            conditionbeforedays: 'BBBBBB',
            conditionleastdays: 'BBBBBB',
            conditionleastroomnum: 'BBBBBB',
            paymentype: 'BBBBBB',
            rateplandesc: 'BBBBBB',
            rateplanname: 'BBBBBB',
            rateplanstate: 'BBBBBB',
            addvaluebednum: 'BBBBBB',
            addvaluebedprice: 'BBBBBB',
            addvaluebreakfastnum: 'BBBBBB',
            addvaluebreakfastprice: 'BBBBBB',
            baseprice: 'BBBBBB',
            saleprice: 'BBBBBB',
            marketprice: 'BBBBBB',
            hotelproductservice: 'BBBBBB',
            hotelproductservicedesc: 'BBBBBB',
            hotelproductid: 'BBBBBB',
            roomid: 'BBBBBB',
            hotelid: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a BookYst', () => {
        const patchObject = Object.assign(
          {
            roomcode: 'BBBBBB',
            roomname: 'BBBBBB',
            roomnum: 'BBBBBB',
            bedids: 'BBBBBB',
            roomsize: 'BBBBBB',
            roomfloor: 'BBBBBB',
            nettype: 'BBBBBB',
            iswindow: 'BBBBBB',
            remark: 'BBBBBB',
            sortid: 'BBBBBB',
            roomstate: 'BBBBBB',
            source: 'BBBBBB',
            roomdistribution: 'BBBBBB',
            conditionbeforedays: 'BBBBBB',
            conditionleastdays: 'BBBBBB',
            conditionleastroomnum: 'BBBBBB',
            paymentype: 'BBBBBB',
            rateplandesc: 'BBBBBB',
            rateplanname: 'BBBBBB',
            addvaluebednum: 'BBBBBB',
            addvaluebreakfastprice: 'BBBBBB',
            baseprice: 'BBBBBB',
            saleprice: 'BBBBBB',
            hotelproductservicedesc: 'BBBBBB',
            roomid: 'BBBBBB',
          },
          new BookYst()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BookYst', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            roomcode: 'BBBBBB',
            roomname: 'BBBBBB',
            roomnum: 'BBBBBB',
            roomseparatenum: 'BBBBBB',
            bedids: 'BBBBBB',
            bedsimpledesc: 'BBBBBB',
            bednum: 'BBBBBB',
            roomsize: 'BBBBBB',
            roomfloor: 'BBBBBB',
            netservice: 'BBBBBB',
            nettype: 'BBBBBB',
            iswindow: 'BBBBBB',
            remark: 'BBBBBB',
            sortid: 'BBBBBB',
            roomstate: 'BBBBBB',
            source: 'BBBBBB',
            roomamenities: 'BBBBBB',
            maxguestnums: 'BBBBBB',
            roomdistribution: 'BBBBBB',
            conditionbeforedays: 'BBBBBB',
            conditionleastdays: 'BBBBBB',
            conditionleastroomnum: 'BBBBBB',
            paymentype: 'BBBBBB',
            rateplandesc: 'BBBBBB',
            rateplanname: 'BBBBBB',
            rateplanstate: 'BBBBBB',
            addvaluebednum: 'BBBBBB',
            addvaluebedprice: 'BBBBBB',
            addvaluebreakfastnum: 'BBBBBB',
            addvaluebreakfastprice: 'BBBBBB',
            baseprice: 'BBBBBB',
            saleprice: 'BBBBBB',
            marketprice: 'BBBBBB',
            hotelproductservice: 'BBBBBB',
            hotelproductservicedesc: 'BBBBBB',
            hotelproductid: 'BBBBBB',
            roomid: 'BBBBBB',
            hotelid: 'BBBBBB',
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

      it('should delete a BookYst', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addBookYstToCollectionIfMissing', () => {
        it('should add a BookYst to an empty array', () => {
          const bookYst: IBookYst = { id: 123 };
          expectedResult = service.addBookYstToCollectionIfMissing([], bookYst);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(bookYst);
        });

        it('should not add a BookYst to an array that contains it', () => {
          const bookYst: IBookYst = { id: 123 };
          const bookYstCollection: IBookYst[] = [
            {
              ...bookYst,
            },
            { id: 456 },
          ];
          expectedResult = service.addBookYstToCollectionIfMissing(bookYstCollection, bookYst);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a BookYst to an array that doesn't contain it", () => {
          const bookYst: IBookYst = { id: 123 };
          const bookYstCollection: IBookYst[] = [{ id: 456 }];
          expectedResult = service.addBookYstToCollectionIfMissing(bookYstCollection, bookYst);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(bookYst);
        });

        it('should add only unique BookYst to an array', () => {
          const bookYstArray: IBookYst[] = [{ id: 123 }, { id: 456 }, { id: 92234 }];
          const bookYstCollection: IBookYst[] = [{ id: 123 }];
          expectedResult = service.addBookYstToCollectionIfMissing(bookYstCollection, ...bookYstArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const bookYst: IBookYst = { id: 123 };
          const bookYst2: IBookYst = { id: 456 };
          expectedResult = service.addBookYstToCollectionIfMissing([], bookYst, bookYst2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(bookYst);
          expect(expectedResult).toContain(bookYst2);
        });

        it('should accept null and undefined values', () => {
          const bookYst: IBookYst = { id: 123 };
          expectedResult = service.addBookYstToCollectionIfMissing([], null, bookYst, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(bookYst);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
