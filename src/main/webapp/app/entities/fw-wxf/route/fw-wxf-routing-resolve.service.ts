import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFwWxf, FwWxf } from '../fw-wxf.model';
import { FwWxfService } from '../service/fw-wxf.service';

@Injectable({ providedIn: 'root' })
export class FwWxfRoutingResolveService implements Resolve<IFwWxf> {
  constructor(protected service: FwWxfService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFwWxf> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fwWxf: HttpResponse<FwWxf>) => {
          if (fwWxf.body) {
            return of(fwWxf.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FwWxf());
  }
}
