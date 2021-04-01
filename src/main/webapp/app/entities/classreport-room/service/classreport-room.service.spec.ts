import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IClassreportRoom, ClassreportRoom } from '../classreport-room.model';

import { ClassreportRoomService } from './classreport-room.service';

describe('Service Tests', () => {
  describe('ClassreportRoom Service', () => {
    let service: ClassreportRoomService;
    let httpMock: HttpTestingController;
    let elemDefault: IClassreportRoom;
    let expectedResult: IClassreportRoom | IClassreportRoom[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ClassreportRoomService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        account: 'AAAAAAA',
        roomn: 'AAAAAAA',
        yfj: 0,
        yfj9008: 0,
        yfj9009: 0,
        yfj9007: 0,
        gz: 0,
        ff: 0,
        minibar: 0,
        phone: 0,
        other: 0,
        pc: 0,
        cz: 0,
        cy: 0,
        md: 0,
        huiy: 0,
        dtb: 0,
        sszx: 0,
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

      it('should create a ClassreportRoom', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ClassreportRoom()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ClassreportRoom', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            account: 'BBBBBB',
            roomn: 'BBBBBB',
            yfj: 1,
            yfj9008: 1,
            yfj9009: 1,
            yfj9007: 1,
            gz: 1,
            ff: 1,
            minibar: 1,
            phone: 1,
            other: 1,
            pc: 1,
            cz: 1,
            cy: 1,
            md: 1,
            huiy: 1,
            dtb: 1,
            sszx: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ClassreportRoom', () => {
        const patchObject = Object.assign(
          {
            account: 'BBBBBB',
            yfj: 1,
            yfj9008: 1,
            gz: 1,
            ff: 1,
            phone: 1,
            dtb: 1,
          },
          new ClassreportRoom()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ClassreportRoom', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            account: 'BBBBBB',
            roomn: 'BBBBBB',
            yfj: 1,
            yfj9008: 1,
            yfj9009: 1,
            yfj9007: 1,
            gz: 1,
            ff: 1,
            minibar: 1,
            phone: 1,
            other: 1,
            pc: 1,
            cz: 1,
            cy: 1,
            md: 1,
            huiy: 1,
            dtb: 1,
            sszx: 1,
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

      it('should delete a ClassreportRoom', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addClassreportRoomToCollectionIfMissing', () => {
        it('should add a ClassreportRoom to an empty array', () => {
          const classreportRoom: IClassreportRoom = { id: 123 };
          expectedResult = service.addClassreportRoomToCollectionIfMissing([], classreportRoom);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(classreportRoom);
        });

        it('should not add a ClassreportRoom to an array that contains it', () => {
          const classreportRoom: IClassreportRoom = { id: 123 };
          const classreportRoomCollection: IClassreportRoom[] = [
            {
              ...classreportRoom,
            },
            { id: 456 },
          ];
          expectedResult = service.addClassreportRoomToCollectionIfMissing(classreportRoomCollection, classreportRoom);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ClassreportRoom to an array that doesn't contain it", () => {
          const classreportRoom: IClassreportRoom = { id: 123 };
          const classreportRoomCollection: IClassreportRoom[] = [{ id: 456 }];
          expectedResult = service.addClassreportRoomToCollectionIfMissing(classreportRoomCollection, classreportRoom);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(classreportRoom);
        });

        it('should add only unique ClassreportRoom to an array', () => {
          const classreportRoomArray: IClassreportRoom[] = [{ id: 123 }, { id: 456 }, { id: 94243 }];
          const classreportRoomCollection: IClassreportRoom[] = [{ id: 123 }];
          expectedResult = service.addClassreportRoomToCollectionIfMissing(classreportRoomCollection, ...classreportRoomArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const classreportRoom: IClassreportRoom = { id: 123 };
          const classreportRoom2: IClassreportRoom = { id: 456 };
          expectedResult = service.addClassreportRoomToCollectionIfMissing([], classreportRoom, classreportRoom2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(classreportRoom);
          expect(expectedResult).toContain(classreportRoom2);
        });

        it('should accept null and undefined values', () => {
          const classreportRoom: IClassreportRoom = { id: 123 };
          expectedResult = service.addClassreportRoomToCollectionIfMissing([], null, classreportRoom, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(classreportRoom);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
