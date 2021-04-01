import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDRk, DRk } from '../d-rk.model';
import { DRkService } from '../service/d-rk.service';

@Injectable({ providedIn: 'root' })
export class DRkRoutingResolveService implements Resolve<IDRk> {
  constructor(protected service: DRkService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDRk> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dRk: HttpResponse<DRk>) => {
          if (dRk.body) {
            return of(dRk.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DRk());
  }
}
