import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICheckCzl, CheckCzl } from '../check-czl.model';
import { CheckCzlService } from '../service/check-czl.service';

@Injectable({ providedIn: 'root' })
export class CheckCzlRoutingResolveService implements Resolve<ICheckCzl> {
  constructor(protected service: CheckCzlService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICheckCzl> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((checkCzl: HttpResponse<CheckCzl>) => {
          if (checkCzl.body) {
            return of(checkCzl.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CheckCzl());
  }
}
