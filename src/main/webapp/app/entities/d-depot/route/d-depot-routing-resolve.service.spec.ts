jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDDepot, DDepot } from '../d-depot.model';
import { DDepotService } from '../service/d-depot.service';

import { DDepotRoutingResolveService } from './d-depot-routing-resolve.service';

describe('Service Tests', () => {
  describe('DDepot routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DDepotRoutingResolveService;
    let service: DDepotService;
    let resultDDepot: IDDepot | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DDepotRoutingResolveService);
      service = TestBed.inject(DDepotService);
      resultDDepot = undefined;
    });

    describe('resolve', () => {
      it('should return IDDepot returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDDepot = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDDepot).toEqual({ id: 123 });
      });

      it('should return new IDDepot if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDDepot = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDDepot).toEqual(new DDepot());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDDepot = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDDepot).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
