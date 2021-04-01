jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDPdb, DPdb } from '../d-pdb.model';
import { DPdbService } from '../service/d-pdb.service';

import { DPdbRoutingResolveService } from './d-pdb-routing-resolve.service';

describe('Service Tests', () => {
  describe('DPdb routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DPdbRoutingResolveService;
    let service: DPdbService;
    let resultDPdb: IDPdb | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DPdbRoutingResolveService);
      service = TestBed.inject(DPdbService);
      resultDPdb = undefined;
    });

    describe('resolve', () => {
      it('should return IDPdb returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDPdb = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDPdb).toEqual({ id: 123 });
      });

      it('should return new IDPdb if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDPdb = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDPdb).toEqual(new DPdb());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDPdb = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDPdb).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
