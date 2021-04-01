import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDDepot, DDepot } from '../d-depot.model';
import { DDepotService } from '../service/d-depot.service';

@Injectable({ providedIn: 'root' })
export class DDepotRoutingResolveService implements Resolve<IDDepot> {
  constructor(protected service: DDepotService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDDepot> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dDepot: HttpResponse<DDepot>) => {
          if (dDepot.body) {
            return of(dDepot.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DDepot());
  }
}
