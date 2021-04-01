jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICode1, Code1 } from '../code-1.model';
import { Code1Service } from '../service/code-1.service';

import { Code1RoutingResolveService } from './code-1-routing-resolve.service';

describe('Service Tests', () => {
  describe('Code1 routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: Code1RoutingResolveService;
    let service: Code1Service;
    let resultCode1: ICode1 | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(Code1RoutingResolveService);
      service = TestBed.inject(Code1Service);
      resultCode1 = undefined;
    });

    describe('resolve', () => {
      it('should return ICode1 returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCode1 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCode1).toEqual({ id: 123 });
      });

      it('should return new ICode1 if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCode1 = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCode1).toEqual(new Code1());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCode1 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCode1).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
