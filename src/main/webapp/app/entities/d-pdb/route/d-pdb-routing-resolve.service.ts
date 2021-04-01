import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDPdb, DPdb } from '../d-pdb.model';
import { DPdbService } from '../service/d-pdb.service';

@Injectable({ providedIn: 'root' })
export class DPdbRoutingResolveService implements Resolve<IDPdb> {
  constructor(protected service: DPdbService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDPdb> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dPdb: HttpResponse<DPdb>) => {
          if (dPdb.body) {
            return of(dPdb.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DPdb());
  }
}
