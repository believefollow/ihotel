jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICheckinAccount, CheckinAccount } from '../checkin-account.model';
import { CheckinAccountService } from '../service/checkin-account.service';

import { CheckinAccountRoutingResolveService } from './checkin-account-routing-resolve.service';

describe('Service Tests', () => {
  describe('CheckinAccount routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CheckinAccountRoutingResolveService;
    let service: CheckinAccountService;
    let resultCheckinAccount: ICheckinAccount | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CheckinAccountRoutingResolveService);
      service = TestBed.inject(CheckinAccountService);
      resultCheckinAccount = undefined;
    });

    describe('resolve', () => {
      it('should return ICheckinAccount returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCheckinAccount = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCheckinAccount).toEqual({ id: 123 });
      });

      it('should return new ICheckinAccount if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCheckinAccount = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCheckinAccount).toEqual(new CheckinAccount());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCheckinAccount = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCheckinAccount).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
