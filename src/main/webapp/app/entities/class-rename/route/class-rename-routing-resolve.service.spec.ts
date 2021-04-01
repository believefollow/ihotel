jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IClassRename, ClassRename } from '../class-rename.model';
import { ClassRenameService } from '../service/class-rename.service';

import { ClassRenameRoutingResolveService } from './class-rename-routing-resolve.service';

describe('Service Tests', () => {
  describe('ClassRename routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ClassRenameRoutingResolveService;
    let service: ClassRenameService;
    let resultClassRename: IClassRename | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ClassRenameRoutingResolveService);
      service = TestBed.inject(ClassRenameService);
      resultClassRename = undefined;
    });

    describe('resolve', () => {
      it('should return IClassRename returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultClassRename = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultClassRename).toEqual({ id: 123 });
      });

      it('should return new IClassRename if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultClassRename = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultClassRename).toEqual(new ClassRename());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultClassRename = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultClassRename).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
