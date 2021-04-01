jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICtClass, CtClass } from '../ct-class.model';
import { CtClassService } from '../service/ct-class.service';

import { CtClassRoutingResolveService } from './ct-class-routing-resolve.service';

describe('Service Tests', () => {
  describe('CtClass routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CtClassRoutingResolveService;
    let service: CtClassService;
    let resultCtClass: ICtClass | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CtClassRoutingResolveService);
      service = TestBed.inject(CtClassService);
      resultCtClass = undefined;
    });

    describe('resolve', () => {
      it('should return ICtClass returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCtClass = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCtClass).toEqual({ id: 123 });
      });

      it('should return new ICtClass if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCtClass = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCtClass).toEqual(new CtClass());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCtClass = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCtClass).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
