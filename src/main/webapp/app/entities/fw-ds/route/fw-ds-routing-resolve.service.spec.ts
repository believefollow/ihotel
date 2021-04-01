jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFwDs, FwDs } from '../fw-ds.model';
import { FwDsService } from '../service/fw-ds.service';

import { FwDsRoutingResolveService } from './fw-ds-routing-resolve.service';

describe('Service Tests', () => {
  describe('FwDs routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FwDsRoutingResolveService;
    let service: FwDsService;
    let resultFwDs: IFwDs | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FwDsRoutingResolveService);
      service = TestBed.inject(FwDsService);
      resultFwDs = undefined;
    });

    describe('resolve', () => {
      it('should return IFwDs returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFwDs = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFwDs).toEqual({ id: 123 });
      });

      it('should return new IFwDs if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFwDs = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFwDs).toEqual(new FwDs());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFwDs = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFwDs).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
