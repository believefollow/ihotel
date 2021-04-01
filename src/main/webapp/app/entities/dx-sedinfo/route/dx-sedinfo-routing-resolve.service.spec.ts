jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDxSedinfo, DxSedinfo } from '../dx-sedinfo.model';
import { DxSedinfoService } from '../service/dx-sedinfo.service';

import { DxSedinfoRoutingResolveService } from './dx-sedinfo-routing-resolve.service';

describe('Service Tests', () => {
  describe('DxSedinfo routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DxSedinfoRoutingResolveService;
    let service: DxSedinfoService;
    let resultDxSedinfo: IDxSedinfo | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DxSedinfoRoutingResolveService);
      service = TestBed.inject(DxSedinfoService);
      resultDxSedinfo = undefined;
    });

    describe('resolve', () => {
      it('should return IDxSedinfo returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDxSedinfo = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDxSedinfo).toEqual({ id: 123 });
      });

      it('should return new IDxSedinfo if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDxSedinfo = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDxSedinfo).toEqual(new DxSedinfo());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDxSedinfo = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDxSedinfo).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
