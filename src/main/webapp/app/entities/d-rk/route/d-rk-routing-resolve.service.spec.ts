jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDRk, DRk } from '../d-rk.model';
import { DRkService } from '../service/d-rk.service';

import { DRkRoutingResolveService } from './d-rk-routing-resolve.service';

describe('Service Tests', () => {
  describe('DRk routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DRkRoutingResolveService;
    let service: DRkService;
    let resultDRk: IDRk | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DRkRoutingResolveService);
      service = TestBed.inject(DRkService);
      resultDRk = undefined;
    });

    describe('resolve', () => {
      it('should return IDRk returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDRk = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDRk).toEqual({ id: 123 });
      });

      it('should return new IDRk if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDRk = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDRk).toEqual(new DRk());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDRk = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDRk).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
