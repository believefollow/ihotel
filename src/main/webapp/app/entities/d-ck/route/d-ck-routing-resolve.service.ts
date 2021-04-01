import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDCk, DCk } from '../d-ck.model';
import { DCkService } from '../service/d-ck.service';

@Injectable({ providedIn: 'root' })
export class DCkRoutingResolveService implements Resolve<IDCk> {
  constructor(protected service: DCkService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDCk> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dCk: HttpResponse<DCk>) => {
          if (dCk.body) {
            return of(dCk.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DCk());
  }
}
