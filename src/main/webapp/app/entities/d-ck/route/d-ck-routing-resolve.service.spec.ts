jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDCk, DCk } from '../d-ck.model';
import { DCkService } from '../service/d-ck.service';

import { DCkRoutingResolveService } from './d-ck-routing-resolve.service';

describe('Service Tests', () => {
  describe('DCk routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DCkRoutingResolveService;
    let service: DCkService;
    let resultDCk: IDCk | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DCkRoutingResolveService);
      service = TestBed.inject(DCkService);
      resultDCk = undefined;
    });

    describe('resolve', () => {
      it('should return IDCk returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDCk = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDCk).toEqual({ id: 123 });
      });

      it('should return new IDCk if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDCk = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDCk).toEqual(new DCk());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDCk = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDCk).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
