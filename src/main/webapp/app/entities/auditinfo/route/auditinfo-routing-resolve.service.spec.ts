jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAuditinfo, Auditinfo } from '../auditinfo.model';
import { AuditinfoService } from '../service/auditinfo.service';

import { AuditinfoRoutingResolveService } from './auditinfo-routing-resolve.service';

describe('Service Tests', () => {
  describe('Auditinfo routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AuditinfoRoutingResolveService;
    let service: AuditinfoService;
    let resultAuditinfo: IAuditinfo | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AuditinfoRoutingResolveService);
      service = TestBed.inject(AuditinfoService);
      resultAuditinfo = undefined;
    });

    describe('resolve', () => {
      it('should return IAuditinfo returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAuditinfo = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAuditinfo).toEqual({ id: 123 });
      });

      it('should return new IAuditinfo if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAuditinfo = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAuditinfo).toEqual(new Auditinfo());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAuditinfo = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAuditinfo).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
