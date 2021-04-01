jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICrinfo, Crinfo } from '../crinfo.model';
import { CrinfoService } from '../service/crinfo.service';

import { CrinfoRoutingResolveService } from './crinfo-routing-resolve.service';

describe('Service Tests', () => {
  describe('Crinfo routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CrinfoRoutingResolveService;
    let service: CrinfoService;
    let resultCrinfo: ICrinfo | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CrinfoRoutingResolveService);
      service = TestBed.inject(CrinfoService);
      resultCrinfo = undefined;
    });

    describe('resolve', () => {
      it('should return ICrinfo returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCrinfo = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCrinfo).toEqual({ id: 123 });
      });

      it('should return new ICrinfo if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCrinfo = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCrinfo).toEqual(new Crinfo());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCrinfo = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCrinfo).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
