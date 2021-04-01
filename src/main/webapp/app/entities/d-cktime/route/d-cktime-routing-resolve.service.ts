import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDCktime, DCktime } from '../d-cktime.model';
import { DCktimeService } from '../service/d-cktime.service';

@Injectable({ providedIn: 'root' })
export class DCktimeRoutingResolveService implements Resolve<IDCktime> {
  constructor(protected service: DCktimeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDCktime> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dCktime: HttpResponse<DCktime>) => {
          if (dCktime.body) {
            return of(dCktime.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DCktime());
  }
}
