jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDCktype, DCktype } from '../d-cktype.model';
import { DCktypeService } from '../service/d-cktype.service';

import { DCktypeRoutingResolveService } from './d-cktype-routing-resolve.service';

describe('Service Tests', () => {
  describe('DCktype routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DCktypeRoutingResolveService;
    let service: DCktypeService;
    let resultDCktype: IDCktype | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DCktypeRoutingResolveService);
      service = TestBed.inject(DCktypeService);
      resultDCktype = undefined;
    });

    describe('resolve', () => {
      it('should return IDCktype returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDCktype = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDCktype).toEqual({ id: 123 });
      });

      it('should return new IDCktype if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDCktype = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDCktype).toEqual(new DCktype());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDCktype = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDCktype).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
