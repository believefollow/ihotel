jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDKc, DKc } from '../d-kc.model';
import { DKcService } from '../service/d-kc.service';

import { DKcRoutingResolveService } from './d-kc-routing-resolve.service';

describe('Service Tests', () => {
  describe('DKc routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DKcRoutingResolveService;
    let service: DKcService;
    let resultDKc: IDKc | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DKcRoutingResolveService);
      service = TestBed.inject(DKcService);
      resultDKc = undefined;
    });

    describe('resolve', () => {
      it('should return IDKc returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDKc = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDKc).toEqual({ id: 123 });
      });

      it('should return new IDKc if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDKc = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDKc).toEqual(new DKc());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDKc = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDKc).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
