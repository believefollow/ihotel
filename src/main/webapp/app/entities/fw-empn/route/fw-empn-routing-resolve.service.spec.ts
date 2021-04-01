jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFwEmpn, FwEmpn } from '../fw-empn.model';
import { FwEmpnService } from '../service/fw-empn.service';

import { FwEmpnRoutingResolveService } from './fw-empn-routing-resolve.service';

describe('Service Tests', () => {
  describe('FwEmpn routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FwEmpnRoutingResolveService;
    let service: FwEmpnService;
    let resultFwEmpn: IFwEmpn | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FwEmpnRoutingResolveService);
      service = TestBed.inject(FwEmpnService);
      resultFwEmpn = undefined;
    });

    describe('resolve', () => {
      it('should return IFwEmpn returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFwEmpn = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFwEmpn).toEqual({ id: 123 });
      });

      it('should return new IFwEmpn if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFwEmpn = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFwEmpn).toEqual(new FwEmpn());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFwEmpn = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFwEmpn).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
