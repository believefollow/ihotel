jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDDb, DDb } from '../d-db.model';
import { DDbService } from '../service/d-db.service';

import { DDbRoutingResolveService } from './d-db-routing-resolve.service';

describe('Service Tests', () => {
  describe('DDb routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DDbRoutingResolveService;
    let service: DDbService;
    let resultDDb: IDDb | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DDbRoutingResolveService);
      service = TestBed.inject(DDbService);
      resultDDb = undefined;
    });

    describe('resolve', () => {
      it('should return IDDb returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDDb = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDDb).toEqual({ id: 123 });
      });

      it('should return new IDDb if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDDb = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDDb).toEqual(new DDb());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDDb = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDDb).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
