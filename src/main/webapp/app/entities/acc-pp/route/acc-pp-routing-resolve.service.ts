import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAccPp, AccPp } from '../acc-pp.model';
import { AccPpService } from '../service/acc-pp.service';

@Injectable({ providedIn: 'root' })
export class AccPpRoutingResolveService implements Resolve<IAccPp> {
  constructor(protected service: AccPpService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccPp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((accPp: HttpResponse<AccPp>) => {
          if (accPp.body) {
            return of(accPp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AccPp());
  }
}
