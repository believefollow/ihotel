jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICzlCz, CzlCz } from '../czl-cz.model';
import { CzlCzService } from '../service/czl-cz.service';

import { CzlCzRoutingResolveService } from './czl-cz-routing-resolve.service';

describe('Service Tests', () => {
  describe('CzlCz routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CzlCzRoutingResolveService;
    let service: CzlCzService;
    let resultCzlCz: ICzlCz | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CzlCzRoutingResolveService);
      service = TestBed.inject(CzlCzService);
      resultCzlCz = undefined;
    });

    describe('resolve', () => {
      it('should return ICzlCz returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCzlCz = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCzlCz).toEqual({ id: 123 });
      });

      it('should return new ICzlCz if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCzlCz = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCzlCz).toEqual(new CzlCz());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCzlCz = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCzlCz).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
