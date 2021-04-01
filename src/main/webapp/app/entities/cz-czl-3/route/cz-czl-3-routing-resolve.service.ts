import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICzCzl3, CzCzl3 } from '../cz-czl-3.model';
import { CzCzl3Service } from '../service/cz-czl-3.service';

@Injectable({ providedIn: 'root' })
export class CzCzl3RoutingResolveService implements Resolve<ICzCzl3> {
  constructor(protected service: CzCzl3Service, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICzCzl3> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((czCzl3: HttpResponse<CzCzl3>) => {
          if (czCzl3.body) {
            return of(czCzl3.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CzCzl3());
  }
}
