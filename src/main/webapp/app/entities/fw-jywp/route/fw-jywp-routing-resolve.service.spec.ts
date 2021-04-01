jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFwJywp, FwJywp } from '../fw-jywp.model';
import { FwJywpService } from '../service/fw-jywp.service';

import { FwJywpRoutingResolveService } from './fw-jywp-routing-resolve.service';

describe('Service Tests', () => {
  describe('FwJywp routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FwJywpRoutingResolveService;
    let service: FwJywpService;
    let resultFwJywp: IFwJywp | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FwJywpRoutingResolveService);
      service = TestBed.inject(FwJywpService);
      resultFwJywp = undefined;
    });

    describe('resolve', () => {
      it('should return IFwJywp returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFwJywp = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFwJywp).toEqual({ id: 123 });
      });

      it('should return new IFwJywp if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFwJywp = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFwJywp).toEqual(new FwJywp());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFwJywp = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFwJywp).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
