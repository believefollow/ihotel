jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICzCzl3, CzCzl3 } from '../cz-czl-3.model';
import { CzCzl3Service } from '../service/cz-czl-3.service';

import { CzCzl3RoutingResolveService } from './cz-czl-3-routing-resolve.service';

describe('Service Tests', () => {
  describe('CzCzl3 routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CzCzl3RoutingResolveService;
    let service: CzCzl3Service;
    let resultCzCzl3: ICzCzl3 | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CzCzl3RoutingResolveService);
      service = TestBed.inject(CzCzl3Service);
      resultCzCzl3 = undefined;
    });

    describe('resolve', () => {
      it('should return ICzCzl3 returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCzCzl3 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCzCzl3).toEqual({ id: 123 });
      });

      it('should return new ICzCzl3 if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCzCzl3 = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCzCzl3).toEqual(new CzCzl3());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCzCzl3 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCzCzl3).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
