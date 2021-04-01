import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICzlCz, CzlCz } from '../czl-cz.model';
import { CzlCzService } from '../service/czl-cz.service';

@Injectable({ providedIn: 'root' })
export class CzlCzRoutingResolveService implements Resolve<ICzlCz> {
  constructor(protected service: CzlCzService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICzlCz> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((czlCz: HttpResponse<CzlCz>) => {
          if (czlCz.body) {
            return of(czlCz.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CzlCz());
  }
}
