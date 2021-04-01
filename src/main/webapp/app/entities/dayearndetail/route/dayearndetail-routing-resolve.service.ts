import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDayearndetail, Dayearndetail } from '../dayearndetail.model';
import { DayearndetailService } from '../service/dayearndetail.service';

@Injectable({ providedIn: 'root' })
export class DayearndetailRoutingResolveService implements Resolve<IDayearndetail> {
  constructor(protected service: DayearndetailService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDayearndetail> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dayearndetail: HttpResponse<Dayearndetail>) => {
          if (dayearndetail.body) {
            return of(dayearndetail.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Dayearndetail());
  }
}
