jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICheckinTz, CheckinTz } from '../checkin-tz.model';
import { CheckinTzService } from '../service/checkin-tz.service';

import { CheckinTzRoutingResolveService } from './checkin-tz-routing-resolve.service';

describe('Service Tests', () => {
  describe('CheckinTz routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CheckinTzRoutingResolveService;
    let service: CheckinTzService;
    let resultCheckinTz: ICheckinTz | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CheckinTzRoutingResolveService);
      service = TestBed.inject(CheckinTzService);
      resultCheckinTz = undefined;
    });

    describe('resolve', () => {
      it('should return ICheckinTz returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCheckinTz = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCheckinTz).toEqual({ id: 123 });
      });

      it('should return new ICheckinTz if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCheckinTz = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCheckinTz).toEqual(new CheckinTz());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCheckinTz = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCheckinTz).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
