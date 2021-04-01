jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICk2xsy, Ck2xsy } from '../ck-2-xsy.model';
import { Ck2xsyService } from '../service/ck-2-xsy.service';

import { Ck2xsyRoutingResolveService } from './ck-2-xsy-routing-resolve.service';

describe('Service Tests', () => {
  describe('Ck2xsy routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: Ck2xsyRoutingResolveService;
    let service: Ck2xsyService;
    let resultCk2xsy: ICk2xsy | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(Ck2xsyRoutingResolveService);
      service = TestBed.inject(Ck2xsyService);
      resultCk2xsy = undefined;
    });

    describe('resolve', () => {
      it('should return ICk2xsy returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCk2xsy = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCk2xsy).toEqual({ id: 123 });
      });

      it('should return new ICk2xsy if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCk2xsy = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCk2xsy).toEqual(new Ck2xsy());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCk2xsy = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCk2xsy).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
