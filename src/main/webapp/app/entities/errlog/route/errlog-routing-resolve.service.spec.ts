jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IErrlog, Errlog } from '../errlog.model';
import { ErrlogService } from '../service/errlog.service';

import { ErrlogRoutingResolveService } from './errlog-routing-resolve.service';

describe('Service Tests', () => {
  describe('Errlog routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ErrlogRoutingResolveService;
    let service: ErrlogService;
    let resultErrlog: IErrlog | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ErrlogRoutingResolveService);
      service = TestBed.inject(ErrlogService);
      resultErrlog = undefined;
    });

    describe('resolve', () => {
      it('should return IErrlog returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultErrlog = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultErrlog).toEqual({ id: 123 });
      });

      it('should return new IErrlog if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultErrlog = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultErrlog).toEqual(new Errlog());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultErrlog = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultErrlog).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
