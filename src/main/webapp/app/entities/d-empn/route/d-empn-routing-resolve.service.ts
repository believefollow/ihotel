import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDEmpn, DEmpn } from '../d-empn.model';
import { DEmpnService } from '../service/d-empn.service';

@Injectable({ providedIn: 'root' })
export class DEmpnRoutingResolveService implements Resolve<IDEmpn> {
  constructor(protected service: DEmpnService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDEmpn> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dEmpn: HttpResponse<DEmpn>) => {
          if (dEmpn.body) {
            return of(dEmpn.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DEmpn());
  }
}
