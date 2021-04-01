jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAccPp, AccPp } from '../acc-pp.model';
import { AccPpService } from '../service/acc-pp.service';

import { AccPpRoutingResolveService } from './acc-pp-routing-resolve.service';

describe('Service Tests', () => {
  describe('AccPp routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AccPpRoutingResolveService;
    let service: AccPpService;
    let resultAccPp: IAccPp | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AccPpRoutingResolveService);
      service = TestBed.inject(AccPpService);
      resultAccPp = undefined;
    });

    describe('resolve', () => {
      it('should return IAccPp returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAccPp = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAccPp).toEqual({ id: 123 });
      });

      it('should return new IAccPp if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAccPp = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAccPp).toEqual(new AccPp());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAccPp = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAccPp).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
