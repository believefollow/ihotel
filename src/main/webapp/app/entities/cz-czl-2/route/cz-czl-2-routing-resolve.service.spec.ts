jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICzCzl2, CzCzl2 } from '../cz-czl-2.model';
import { CzCzl2Service } from '../service/cz-czl-2.service';

import { CzCzl2RoutingResolveService } from './cz-czl-2-routing-resolve.service';

describe('Service Tests', () => {
  describe('CzCzl2 routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CzCzl2RoutingResolveService;
    let service: CzCzl2Service;
    let resultCzCzl2: ICzCzl2 | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CzCzl2RoutingResolveService);
      service = TestBed.inject(CzCzl2Service);
      resultCzCzl2 = undefined;
    });

    describe('resolve', () => {
      it('should return ICzCzl2 returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCzCzl2 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCzCzl2).toEqual({ id: 123 });
      });

      it('should return new ICzCzl2 if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCzCzl2 = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCzCzl2).toEqual(new CzCzl2());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCzCzl2 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCzCzl2).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
