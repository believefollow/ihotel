import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICzBqz, CzBqz } from '../cz-bqz.model';
import { CzBqzService } from '../service/cz-bqz.service';

@Injectable({ providedIn: 'root' })
export class CzBqzRoutingResolveService implements Resolve<ICzBqz> {
  constructor(protected service: CzBqzService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICzBqz> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((czBqz: HttpResponse<CzBqz>) => {
          if (czBqz.body) {
            return of(czBqz.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CzBqz());
  }
}
