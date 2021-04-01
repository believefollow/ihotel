jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDXs, DXs } from '../d-xs.model';
import { DXsService } from '../service/d-xs.service';

import { DXsRoutingResolveService } from './d-xs-routing-resolve.service';

describe('Service Tests', () => {
  describe('DXs routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DXsRoutingResolveService;
    let service: DXsService;
    let resultDXs: IDXs | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DXsRoutingResolveService);
      service = TestBed.inject(DXsService);
      resultDXs = undefined;
    });

    describe('resolve', () => {
      it('should return IDXs returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDXs = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDXs).toEqual({ id: 123 });
      });

      it('should return new IDXs if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDXs = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDXs).toEqual(new DXs());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDXs = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDXs).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
