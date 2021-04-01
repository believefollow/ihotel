jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICheckin, Checkin } from '../checkin.model';
import { CheckinService } from '../service/checkin.service';

import { CheckinRoutingResolveService } from './checkin-routing-resolve.service';

describe('Service Tests', () => {
  describe('Checkin routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CheckinRoutingResolveService;
    let service: CheckinService;
    let resultCheckin: ICheckin | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CheckinRoutingResolveService);
      service = TestBed.inject(CheckinService);
      resultCheckin = undefined;
    });

    describe('resolve', () => {
      it('should return ICheckin returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCheckin = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCheckin).toEqual({ id: 123 });
      });

      it('should return new ICheckin if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCheckin = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCheckin).toEqual(new Checkin());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCheckin = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCheckin).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
