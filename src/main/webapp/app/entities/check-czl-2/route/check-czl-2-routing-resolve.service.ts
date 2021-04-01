import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICheckCzl2, CheckCzl2 } from '../check-czl-2.model';
import { CheckCzl2Service } from '../service/check-czl-2.service';

@Injectable({ providedIn: 'root' })
export class CheckCzl2RoutingResolveService implements Resolve<ICheckCzl2> {
  constructor(protected service: CheckCzl2Service, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICheckCzl2> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((checkCzl2: HttpResponse<CheckCzl2>) => {
          if (checkCzl2.body) {
            return of(checkCzl2.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CheckCzl2());
  }
}
