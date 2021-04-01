jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDDept, DDept } from '../d-dept.model';
import { DDeptService } from '../service/d-dept.service';

import { DDeptRoutingResolveService } from './d-dept-routing-resolve.service';

describe('Service Tests', () => {
  describe('DDept routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DDeptRoutingResolveService;
    let service: DDeptService;
    let resultDDept: IDDept | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DDeptRoutingResolveService);
      service = TestBed.inject(DDeptService);
      resultDDept = undefined;
    });

    describe('resolve', () => {
      it('should return IDDept returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDDept = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDDept).toEqual({ id: 123 });
      });

      it('should return new IDDept if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDDept = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDDept).toEqual(new DDept());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDDept = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDDept).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
