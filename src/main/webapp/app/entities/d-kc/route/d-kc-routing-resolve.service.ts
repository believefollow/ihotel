import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDKc, DKc } from '../d-kc.model';
import { DKcService } from '../service/d-kc.service';

@Injectable({ providedIn: 'root' })
export class DKcRoutingResolveService implements Resolve<IDKc> {
  constructor(protected service: DKcService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDKc> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dKc: HttpResponse<DKc>) => {
          if (dKc.body) {
            return of(dKc.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DKc());
  }
}
