import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdhoc, Adhoc } from '../adhoc.model';
import { AdhocService } from '../service/adhoc.service';

@Injectable({ providedIn: 'root' })
export class AdhocRoutingResolveService implements Resolve<IAdhoc> {
  constructor(protected service: AdhocService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdhoc> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((adhoc: HttpResponse<Adhoc>) => {
          if (adhoc.body) {
            return of(adhoc.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Adhoc());
  }
}
