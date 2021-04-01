jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICheckCzl2, CheckCzl2 } from '../check-czl-2.model';
import { CheckCzl2Service } from '../service/check-czl-2.service';

import { CheckCzl2RoutingResolveService } from './check-czl-2-routing-resolve.service';

describe('Service Tests', () => {
  describe('CheckCzl2 routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CheckCzl2RoutingResolveService;
    let service: CheckCzl2Service;
    let resultCheckCzl2: ICheckCzl2 | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CheckCzl2RoutingResolveService);
      service = TestBed.inject(CheckCzl2Service);
      resultCheckCzl2 = undefined;
    });

    describe('resolve', () => {
      it('should return ICheckCzl2 returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCheckCzl2 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCheckCzl2).toEqual({ id: 123 });
      });

      it('should return new ICheckCzl2 if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCheckCzl2 = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCheckCzl2).toEqual(new CheckCzl2());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCheckCzl2 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCheckCzl2).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
