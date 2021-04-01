jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICyRoomtype, CyRoomtype } from '../cy-roomtype.model';
import { CyRoomtypeService } from '../service/cy-roomtype.service';

import { CyRoomtypeRoutingResolveService } from './cy-roomtype-routing-resolve.service';

describe('Service Tests', () => {
  describe('CyRoomtype routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CyRoomtypeRoutingResolveService;
    let service: CyRoomtypeService;
    let resultCyRoomtype: ICyRoomtype | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CyRoomtypeRoutingResolveService);
      service = TestBed.inject(CyRoomtypeService);
      resultCyRoomtype = undefined;
    });

    describe('resolve', () => {
      it('should return ICyRoomtype returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCyRoomtype = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCyRoomtype).toEqual({ id: 123 });
      });

      it('should return new ICyRoomtype if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCyRoomtype = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCyRoomtype).toEqual(new CyRoomtype());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCyRoomtype = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCyRoomtype).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
