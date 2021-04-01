jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IBookYst, BookYst } from '../book-yst.model';
import { BookYstService } from '../service/book-yst.service';

import { BookYstRoutingResolveService } from './book-yst-routing-resolve.service';

describe('Service Tests', () => {
  describe('BookYst routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: BookYstRoutingResolveService;
    let service: BookYstService;
    let resultBookYst: IBookYst | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(BookYstRoutingResolveService);
      service = TestBed.inject(BookYstService);
      resultBookYst = undefined;
    });

    describe('resolve', () => {
      it('should return IBookYst returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBookYst = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultBookYst).toEqual({ id: 123 });
      });

      it('should return new IBookYst if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBookYst = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultBookYst).toEqual(new BookYst());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBookYst = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultBookYst).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
