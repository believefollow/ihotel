jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICzBqz, CzBqz } from '../cz-bqz.model';
import { CzBqzService } from '../service/cz-bqz.service';

import { CzBqzRoutingResolveService } from './cz-bqz-routing-resolve.service';

describe('Service Tests', () => {
  describe('CzBqz routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CzBqzRoutingResolveService;
    let service: CzBqzService;
    let resultCzBqz: ICzBqz | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CzBqzRoutingResolveService);
      service = TestBed.inject(CzBqzService);
      resultCzBqz = undefined;
    });

    describe('resolve', () => {
      it('should return ICzBqz returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCzBqz = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCzBqz).toEqual({ id: 123 });
      });

      it('should return new ICzBqz if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCzBqz = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCzBqz).toEqual(new CzBqz());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCzBqz = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCzBqz).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
