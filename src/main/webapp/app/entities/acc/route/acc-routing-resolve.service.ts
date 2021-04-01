import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAcc, Acc } from '../acc.model';
import { AccService } from '../service/acc.service';

@Injectable({ providedIn: 'root' })
export class AccRoutingResolveService implements Resolve<IAcc> {
  constructor(protected service: AccService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAcc> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((acc: HttpResponse<Acc>) => {
          if (acc.body) {
            return of(acc.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Acc());
  }
}
