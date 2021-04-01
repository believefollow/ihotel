import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IClog, Clog } from '../clog.model';
import { ClogService } from '../service/clog.service';

@Injectable({ providedIn: 'root' })
export class ClogRoutingResolveService implements Resolve<IClog> {
  constructor(protected service: ClogService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClog> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((clog: HttpResponse<Clog>) => {
          if (clog.body) {
            return of(clog.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Clog());
  }
}
