import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICzCzl2, CzCzl2 } from '../cz-czl-2.model';
import { CzCzl2Service } from '../service/cz-czl-2.service';

@Injectable({ providedIn: 'root' })
export class CzCzl2RoutingResolveService implements Resolve<ICzCzl2> {
  constructor(protected service: CzCzl2Service, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICzCzl2> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((czCzl2: HttpResponse<CzCzl2>) => {
          if (czCzl2.body) {
            return of(czCzl2.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CzCzl2());
  }
}
