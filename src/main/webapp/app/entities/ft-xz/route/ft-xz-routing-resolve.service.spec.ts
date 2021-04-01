jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFtXz, FtXz } from '../ft-xz.model';
import { FtXzService } from '../service/ft-xz.service';

import { FtXzRoutingResolveService } from './ft-xz-routing-resolve.service';

describe('Service Tests', () => {
  describe('FtXz routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FtXzRoutingResolveService;
    let service: FtXzService;
    let resultFtXz: IFtXz | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FtXzRoutingResolveService);
      service = TestBed.inject(FtXzService);
      resultFtXz = undefined;
    });

    describe('resolve', () => {
      it('should return IFtXz returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFtXz = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFtXz).toEqual({ id: 123 });
      });

      it('should return new IFtXz if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFtXz = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFtXz).toEqual(new FtXz());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFtXz = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFtXz).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
