jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDayearndetail, Dayearndetail } from '../dayearndetail.model';
import { DayearndetailService } from '../service/dayearndetail.service';

import { DayearndetailRoutingResolveService } from './dayearndetail-routing-resolve.service';

describe('Service Tests', () => {
  describe('Dayearndetail routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DayearndetailRoutingResolveService;
    let service: DayearndetailService;
    let resultDayearndetail: IDayearndetail | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DayearndetailRoutingResolveService);
      service = TestBed.inject(DayearndetailService);
      resultDayearndetail = undefined;
    });

    describe('resolve', () => {
      it('should return IDayearndetail returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDayearndetail = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDayearndetail).toEqual({ id: 123 });
      });

      it('should return new IDayearndetail if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDayearndetail = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDayearndetail).toEqual(new Dayearndetail());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDayearndetail = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDayearndetail).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});