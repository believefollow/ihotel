jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFtXzs, FtXzs } from '../ft-xzs.model';
import { FtXzsService } from '../service/ft-xzs.service';

import { FtXzsRoutingResolveService } from './ft-xzs-routing-resolve.service';

describe('Service Tests', () => {
  describe('FtXzs routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FtXzsRoutingResolveService;
    let service: FtXzsService;
    let resultFtXzs: IFtXzs | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FtXzsRoutingResolveService);
      service = TestBed.inject(FtXzsService);
      resultFtXzs = undefined;
    });

    describe('resolve', () => {
      it('should return IFtXzs returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFtXzs = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFtXzs).toEqual({ id: 123 });
      });

      it('should return new IFtXzs if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFtXzs = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFtXzs).toEqual(new FtXzs());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFtXzs = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFtXzs).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
