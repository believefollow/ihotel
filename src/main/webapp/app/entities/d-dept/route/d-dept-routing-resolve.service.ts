import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDDept, DDept } from '../d-dept.model';
import { DDeptService } from '../service/d-dept.service';

@Injectable({ providedIn: 'root' })
export class DDeptRoutingResolveService implements Resolve<IDDept> {
  constructor(protected service: DDeptService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDDept> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dDept: HttpResponse<DDept>) => {
          if (dDept.body) {
            return of(dDept.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DDept());
  }
}
