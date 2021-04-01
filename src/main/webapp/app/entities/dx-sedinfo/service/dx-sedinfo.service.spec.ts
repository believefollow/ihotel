import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDxSedinfo, DxSedinfo } from '../dx-sedinfo.model';

import { DxSedinfoService } from './dx-sedinfo.service';

describe('Service Tests', () => {
  describe('DxSedinfo Service', () => {
    let service: DxSedinfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IDxSedinfo;
    let expectedResult: IDxSedinfo | IDxSedinfo[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DxSedinfoService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        yddx: 'AAAAAAA',
        yddxmemo: 'AAAAAAA',
        qxyddx: 'AAAAAAA',
        qxydmemo: 'AAAAAAA',
        czdx: 'AAAAAAA',
        czmemo: 'AAAAAAA',
        qxczdx: 'AAAAAAA',
        qxczmemo: 'AAAAAAA',
        yyedx: 'AAAAAAA',
        yyememo: 'AAAAAAA',
        fstime: 'AAAAAAA',
        sffshm: 'AAAAAAA',
        rzdx: 'AAAAAAA',
        rzdxroomn: 'AAAAAAA',
        jfdz: 'AAAAAAA',
        blhy: 'AAAAAAA',
        rzmemo: 'AAAAAAA',
        blhymemo: 'AAAAAAA',
        tfdx: 'AAAAAAA',
        tfdxmemo: 'AAAAAAA',
        fslb: 'AAAAAAA',
        fslbmemo: 'AAAAAAA',
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

      it('should create a DxSedinfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DxSedinfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DxSedinfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            yddx: 'BBBBBB',
            yddxmemo: 'BBBBBB',
            qxyddx: 'BBBBBB',
            qxydmemo: 'BBBBBB',
            czdx: 'BBBBBB',
            czmemo: 'BBBBBB',
            qxczdx: 'BBBBBB',
            qxczmemo: 'BBBBBB',
            yyedx: 'BBBBBB',
            yyememo: 'BBBBBB',
            fstime: 'BBBBBB',
            sffshm: 'BBBBBB',
            rzdx: 'BBBBBB',
            rzdxroomn: 'BBBBBB',
            jfdz: 'BBBBBB',
            blhy: 'BBBBBB',
            rzmemo: 'BBBBBB',
            blhymemo: 'BBBBBB',
            tfdx: 'BBBBBB',
            tfdxmemo: 'BBBBBB',
            fslb: 'BBBBBB',
            fslbmemo: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DxSedinfo', () => {
        const patchObject = Object.assign(
          {
            yddx: 'BBBBBB',
            yddxmemo: 'BBBBBB',
            czdx: 'BBBBBB',
            czmemo: 'BBBBBB',
            qxczdx: 'BBBBBB',
            qxczmemo: 'BBBBBB',
            yyememo: 'BBBBBB',
            fstime: 'BBBBBB',
            rzdxroomn: 'BBBBBB',
            jfdz: 'BBBBBB',
            rzmemo: 'BBBBBB',
            tfdxmemo: 'BBBBBB',
            fslbmemo: 'BBBBBB',
          },
          new DxSedinfo()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DxSedinfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            yddx: 'BBBBBB',
            yddxmemo: 'BBBBBB',
            qxyddx: 'BBBBBB',
            qxydmemo: 'BBBBBB',
            czdx: 'BBBBBB',
            czmemo: 'BBBBBB',
            qxczdx: 'BBBBBB',
            qxczmemo: 'BBBBBB',
            yyedx: 'BBBBBB',
            yyememo: 'BBBBBB',
            fstime: 'BBBBBB',
            sffshm: 'BBBBBB',
            rzdx: 'BBBBBB',
            rzdxroomn: 'BBBBBB',
            jfdz: 'BBBBBB',
            blhy: 'BBBBBB',
            rzmemo: 'BBBBBB',
            blhymemo: 'BBBBBB',
            tfdx: 'BBBBBB',
            tfdxmemo: 'BBBBBB',
            fslb: 'BBBBBB',
            fslbmemo: 'BBBBBB',
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

      it('should delete a DxSedinfo', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDxSedinfoToCollectionIfMissing', () => {
        it('should add a DxSedinfo to an empty array', () => {
          const dxSedinfo: IDxSedinfo = { id: 123 };
          expectedResult = service.addDxSedinfoToCollectionIfMissing([], dxSedinfo);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dxSedinfo);
        });

        it('should not add a DxSedinfo to an array that contains it', () => {
          const dxSedinfo: IDxSedinfo = { id: 123 };
          const dxSedinfoCollection: IDxSedinfo[] = [
            {
              ...dxSedinfo,
            },
            { id: 456 },
          ];
          expectedResult = service.addDxSedinfoToCollectionIfMissing(dxSedinfoCollection, dxSedinfo);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DxSedinfo to an array that doesn't contain it", () => {
          const dxSedinfo: IDxSedinfo = { id: 123 };
          const dxSedinfoCollection: IDxSedinfo[] = [{ id: 456 }];
          expectedResult = service.addDxSedinfoToCollectionIfMissing(dxSedinfoCollection, dxSedinfo);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dxSedinfo);
        });

        it('should add only unique DxSedinfo to an array', () => {
          const dxSedinfoArray: IDxSedinfo[] = [{ id: 123 }, { id: 456 }, { id: 84992 }];
          const dxSedinfoCollection: IDxSedinfo[] = [{ id: 123 }];
          expectedResult = service.addDxSedinfoToCollectionIfMissing(dxSedinfoCollection, ...dxSedinfoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const dxSedinfo: IDxSedinfo = { id: 123 };
          const dxSedinfo2: IDxSedinfo = { id: 456 };
          expectedResult = service.addDxSedinfoToCollectionIfMissing([], dxSedinfo, dxSedinfo2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(dxSedinfo);
          expect(expectedResult).toContain(dxSedinfo2);
        });

        it('should accept null and undefined values', () => {
          const dxSedinfo: IDxSedinfo = { id: 123 };
          expectedResult = service.addDxSedinfoToCollectionIfMissing([], null, dxSedinfo, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(dxSedinfo);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
