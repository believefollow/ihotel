import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFwEmpn, FwEmpn } from '../fw-empn.model';
import { FwEmpnService } from '../service/fw-empn.service';

@Injectable({ providedIn: 'root' })
export class FwEmpnRoutingResolveService implements Resolve<IFwEmpn> {
  constructor(protected service: FwEmpnService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFwEmpn> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fwEmpn: HttpResponse<FwEmpn>) => {
          if (fwEmpn.body) {
            return of(fwEmpn.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FwEmpn());
  }
}
