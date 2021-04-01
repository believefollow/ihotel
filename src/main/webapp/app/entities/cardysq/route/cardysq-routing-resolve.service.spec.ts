jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICardysq, Cardysq } from '../cardysq.model';
import { CardysqService } from '../service/cardysq.service';

import { CardysqRoutingResolveService } from './cardysq-routing-resolve.service';

describe('Service Tests', () => {
  describe('Cardysq routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CardysqRoutingResolveService;
    let service: CardysqService;
    let resultCardysq: ICardysq | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CardysqRoutingResolveService);
      service = TestBed.inject(CardysqService);
      resultCardysq = undefined;
    });

    describe('resolve', () => {
      it('should return ICardysq returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCardysq = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCardysq).toEqual({ id: 123 });
      });

      it('should return new ICardysq if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCardysq = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCardysq).toEqual(new Cardysq());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCardysq = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCardysq).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
