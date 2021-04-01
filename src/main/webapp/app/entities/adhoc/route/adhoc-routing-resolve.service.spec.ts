jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAdhoc, Adhoc } from '../adhoc.model';
import { AdhocService } from '../service/adhoc.service';

import { AdhocRoutingResolveService } from './adhoc-routing-resolve.service';

describe('Service Tests', () => {
  describe('Adhoc routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AdhocRoutingResolveService;
    let service: AdhocService;
    let resultAdhoc: IAdhoc | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AdhocRoutingResolveService);
      service = TestBed.inject(AdhocService);
      resultAdhoc = undefined;
    });

    describe('resolve', () => {
      it('should return IAdhoc returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 'ABC' };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAdhoc = result;
        });

        // THEN
        expect(service.find).toBeCalledWith('ABC');
        expect(resultAdhoc).toEqual({ id: 'ABC' });
      });

      it('should return new IAdhoc if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAdhoc = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAdhoc).toEqual(new Adhoc());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 'ABC' };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAdhoc = result;
        });

        // THEN
        expect(service.find).toBeCalledWith('ABC');
        expect(resultAdhoc).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
