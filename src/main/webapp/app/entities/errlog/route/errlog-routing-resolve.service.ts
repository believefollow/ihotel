import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IErrlog, Errlog } from '../errlog.model';
import { ErrlogService } from '../service/errlog.service';

@Injectable({ providedIn: 'root' })
export class ErrlogRoutingResolveService implements Resolve<IErrlog> {
  constructor(protected service: ErrlogService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IErrlog> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((errlog: HttpResponse<Errlog>) => {
          if (errlog.body) {
            return of(errlog.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Errlog());
  }
}
