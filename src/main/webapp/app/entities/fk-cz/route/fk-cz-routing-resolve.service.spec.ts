jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFkCz, FkCz } from '../fk-cz.model';
import { FkCzService } from '../service/fk-cz.service';

import { FkCzRoutingResolveService } from './fk-cz-routing-resolve.service';

describe('Service Tests', () => {
  describe('FkCz routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FkCzRoutingResolveService;
    let service: FkCzService;
    let resultFkCz: IFkCz | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FkCzRoutingResolveService);
      service = TestBed.inject(FkCzService);
      resultFkCz = undefined;
    });

    describe('resolve', () => {
      it('should return IFkCz returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFkCz = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFkCz).toEqual({ id: 123 });
      });

      it('should return new IFkCz if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFkCz = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFkCz).toEqual(new FkCz());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFkCz = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFkCz).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
