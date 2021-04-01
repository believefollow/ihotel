import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFwDs, FwDs } from '../fw-ds.model';
import { FwDsService } from '../service/fw-ds.service';

@Injectable({ providedIn: 'root' })
export class FwDsRoutingResolveService implements Resolve<IFwDs> {
  constructor(protected service: FwDsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFwDs> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fwDs: HttpResponse<FwDs>) => {
          if (fwDs.body) {
            return of(fwDs.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FwDs());
  }
}
