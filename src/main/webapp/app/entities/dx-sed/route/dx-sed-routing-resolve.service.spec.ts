jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDxSed, DxSed } from '../dx-sed.model';
import { DxSedService } from '../service/dx-sed.service';

import { DxSedRoutingResolveService } from './dx-sed-routing-resolve.service';

describe('Service Tests', () => {
  describe('DxSed routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DxSedRoutingResolveService;
    let service: DxSedService;
    let resultDxSed: IDxSed | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DxSedRoutingResolveService);
      service = TestBed.inject(DxSedService);
      resultDxSed = undefined;
    });

    describe('resolve', () => {
      it('should return IDxSed returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDxSed = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDxSed).toEqual({ id: 123 });
      });

      it('should return new IDxSed if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDxSed = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDxSed).toEqual(new DxSed());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDxSed = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDxSed).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
