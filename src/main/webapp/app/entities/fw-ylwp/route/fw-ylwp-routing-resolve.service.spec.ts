jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFwYlwp, FwYlwp } from '../fw-ylwp.model';
import { FwYlwpService } from '../service/fw-ylwp.service';

import { FwYlwpRoutingResolveService } from './fw-ylwp-routing-resolve.service';

describe('Service Tests', () => {
  describe('FwYlwp routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FwYlwpRoutingResolveService;
    let service: FwYlwpService;
    let resultFwYlwp: IFwYlwp | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FwYlwpRoutingResolveService);
      service = TestBed.inject(FwYlwpService);
      resultFwYlwp = undefined;
    });

    describe('resolve', () => {
      it('should return IFwYlwp returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFwYlwp = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFwYlwp).toEqual({ id: 123 });
      });

      it('should return new IFwYlwp if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFwYlwp = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFwYlwp).toEqual(new FwYlwp());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFwYlwp = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFwYlwp).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
