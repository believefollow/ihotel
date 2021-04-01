import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEe, Ee } from '../ee.model';
import { EeService } from '../service/ee.service';

@Injectable({ providedIn: 'root' })
export class EeRoutingResolveService implements Resolve<IEe> {
  constructor(protected service: EeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ee: HttpResponse<Ee>) => {
          if (ee.body) {
            return of(ee.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ee());
  }
}
