import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBookingtime, Bookingtime } from '../bookingtime.model';
import { BookingtimeService } from '../service/bookingtime.service';

@Injectable({ providedIn: 'root' })
export class BookingtimeRoutingResolveService implements Resolve<IBookingtime> {
  constructor(protected service: BookingtimeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBookingtime> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bookingtime: HttpResponse<Bookingtime>) => {
          if (bookingtime.body) {
            return of(bookingtime.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Bookingtime());
  }
}
