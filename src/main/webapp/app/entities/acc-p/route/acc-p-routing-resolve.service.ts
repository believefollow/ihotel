import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAccP, AccP } from '../acc-p.model';
import { AccPService } from '../service/acc-p.service';

@Injectable({ providedIn: 'root' })
export class AccPRoutingResolveService implements Resolve<IAccP> {
  constructor(protected service: AccPService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccP> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((accP: HttpResponse<AccP>) => {
          if (accP.body) {
            return of(accP.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AccP());
  }
}
