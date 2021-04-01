import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFwYlwp, FwYlwp } from '../fw-ylwp.model';
import { FwYlwpService } from '../service/fw-ylwp.service';

@Injectable({ providedIn: 'root' })
export class FwYlwpRoutingResolveService implements Resolve<IFwYlwp> {
  constructor(protected service: FwYlwpService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFwYlwp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fwYlwp: HttpResponse<FwYlwp>) => {
          if (fwYlwp.body) {
            return of(fwYlwp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FwYlwp());
  }
}
