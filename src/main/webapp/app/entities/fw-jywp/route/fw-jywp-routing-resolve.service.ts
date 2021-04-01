import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFwJywp, FwJywp } from '../fw-jywp.model';
import { FwJywpService } from '../service/fw-jywp.service';

@Injectable({ providedIn: 'root' })
export class FwJywpRoutingResolveService implements Resolve<IFwJywp> {
  constructor(protected service: FwJywpService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFwJywp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fwJywp: HttpResponse<FwJywp>) => {
          if (fwJywp.body) {
            return of(fwJywp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FwJywp());
  }
}
