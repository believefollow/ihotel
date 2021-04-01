jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDType, DType } from '../d-type.model';
import { DTypeService } from '../service/d-type.service';

import { DTypeRoutingResolveService } from './d-type-routing-resolve.service';

describe('Service Tests', () => {
  describe('DType routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DTypeRoutingResolveService;
    let service: DTypeService;
    let resultDType: IDType | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DTypeRoutingResolveService);
      service = TestBed.inject(DTypeService);
      resultDType = undefined;
    });

    describe('resolve', () => {
      it('should return IDType returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDType = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDType).toEqual({ id: 123 });
      });

      it('should return new IDType if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDType = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDType).toEqual(new DType());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDType = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDType).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
