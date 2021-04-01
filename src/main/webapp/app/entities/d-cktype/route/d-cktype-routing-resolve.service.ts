import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDCktype, DCktype } from '../d-cktype.model';
import { DCktypeService } from '../service/d-cktype.service';

@Injectable({ providedIn: 'root' })
export class DCktypeRoutingResolveService implements Resolve<IDCktype> {
  constructor(protected service: DCktypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDCktype> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dCktype: HttpResponse<DCktype>) => {
          if (dCktype.body) {
            return of(dCktype.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DCktype());
  }
}
