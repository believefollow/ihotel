jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFeetype, Feetype } from '../feetype.model';
import { FeetypeService } from '../service/feetype.service';

import { FeetypeRoutingResolveService } from './feetype-routing-resolve.service';

describe('Service Tests', () => {
  describe('Feetype routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FeetypeRoutingResolveService;
    let service: FeetypeService;
    let resultFeetype: IFeetype | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FeetypeRoutingResolveService);
      service = TestBed.inject(FeetypeService);
      resultFeetype = undefined;
    });

    describe('resolve', () => {
      it('should return IFeetype returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFeetype = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFeetype).toEqual({ id: 123 });
      });

      it('should return new IFeetype if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFeetype = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFeetype).toEqual(new Feetype());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFeetype = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFeetype).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
