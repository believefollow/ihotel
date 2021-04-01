import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDDb, DDb } from '../d-db.model';
import { DDbService } from '../service/d-db.service';

@Injectable({ providedIn: 'root' })
export class DDbRoutingResolveService implements Resolve<IDDb> {
  constructor(protected service: DDbService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDDb> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dDb: HttpResponse<DDb>) => {
          if (dDb.body) {
            return of(dDb.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DDb());
  }
}
