import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFkCz, FkCz } from '../fk-cz.model';
import { FkCzService } from '../service/fk-cz.service';

@Injectable({ providedIn: 'root' })
export class FkCzRoutingResolveService implements Resolve<IFkCz> {
  constructor(protected service: FkCzService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFkCz> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fkCz: HttpResponse<FkCz>) => {
          if (fkCz.body) {
            return of(fkCz.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FkCz());
  }
}
