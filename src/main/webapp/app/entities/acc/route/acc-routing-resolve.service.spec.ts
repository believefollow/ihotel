jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAcc, Acc } from '../acc.model';
import { AccService } from '../service/acc.service';

import { AccRoutingResolveService } from './acc-routing-resolve.service';

describe('Service Tests', () => {
  describe('Acc routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AccRoutingResolveService;
    let service: AccService;
    let resultAcc: IAcc | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AccRoutingResolveService);
      service = TestBed.inject(AccService);
      resultAcc = undefined;
    });

    describe('resolve', () => {
      it('should return IAcc returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAcc = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAcc).toEqual({ id: 123 });
      });

      it('should return new IAcc if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAcc = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAcc).toEqual(new Acc());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAcc = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAcc).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
