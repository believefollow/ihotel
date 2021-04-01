import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDUnit, DUnit } from '../d-unit.model';
import { DUnitService } from '../service/d-unit.service';

@Injectable({ providedIn: 'root' })
export class DUnitRoutingResolveService implements Resolve<IDUnit> {
  constructor(protected service: DUnitService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDUnit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dUnit: HttpResponse<DUnit>) => {
          if (dUnit.body) {
            return of(dUnit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DUnit());
  }
}
