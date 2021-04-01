jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAccounts, Accounts } from '../accounts.model';
import { AccountsService } from '../service/accounts.service';

import { AccountsRoutingResolveService } from './accounts-routing-resolve.service';

describe('Service Tests', () => {
  describe('Accounts routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AccountsRoutingResolveService;
    let service: AccountsService;
    let resultAccounts: IAccounts | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AccountsRoutingResolveService);
      service = TestBed.inject(AccountsService);
      resultAccounts = undefined;
    });

    describe('resolve', () => {
      it('should return IAccounts returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAccounts = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAccounts).toEqual({ id: 123 });
      });

      it('should return new IAccounts if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAccounts = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAccounts).toEqual(new Accounts());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAccounts = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAccounts).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
