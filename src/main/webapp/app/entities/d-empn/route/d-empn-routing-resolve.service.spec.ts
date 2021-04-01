jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDEmpn, DEmpn } from '../d-empn.model';
import { DEmpnService } from '../service/d-empn.service';

import { DEmpnRoutingResolveService } from './d-empn-routing-resolve.service';

describe('Service Tests', () => {
  describe('DEmpn routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DEmpnRoutingResolveService;
    let service: DEmpnService;
    let resultDEmpn: IDEmpn | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DEmpnRoutingResolveService);
      service = TestBed.inject(DEmpnService);
      resultDEmpn = undefined;
    });

    describe('resolve', () => {
      it('should return IDEmpn returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDEmpn = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDEmpn).toEqual({ id: 123 });
      });

      it('should return new IDEmpn if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDEmpn = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDEmpn).toEqual(new DEmpn());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDEmpn = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDEmpn).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
