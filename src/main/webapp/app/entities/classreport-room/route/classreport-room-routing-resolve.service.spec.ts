jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IClassreportRoom, ClassreportRoom } from '../classreport-room.model';
import { ClassreportRoomService } from '../service/classreport-room.service';

import { ClassreportRoomRoutingResolveService } from './classreport-room-routing-resolve.service';

describe('Service Tests', () => {
  describe('ClassreportRoom routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ClassreportRoomRoutingResolveService;
    let service: ClassreportRoomService;
    let resultClassreportRoom: IClassreportRoom | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ClassreportRoomRoutingResolveService);
      service = TestBed.inject(ClassreportRoomService);
      resultClassreportRoom = undefined;
    });

    describe('resolve', () => {
      it('should return IClassreportRoom returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultClassreportRoom = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultClassreportRoom).toEqual({ id: 123 });
      });

      it('should return new IClassreportRoom if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultClassreportRoom = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultClassreportRoom).toEqual(new ClassreportRoom());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultClassreportRoom = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultClassreportRoom).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
