import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICk2xsy, Ck2xsy } from '../ck-2-xsy.model';
import { Ck2xsyService } from '../service/ck-2-xsy.service';

@Injectable({ providedIn: 'root' })
export class Ck2xsyRoutingResolveService implements Resolve<ICk2xsy> {
  constructor(protected service: Ck2xsyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICk2xsy> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ck2xsy: HttpResponse<Ck2xsy>) => {
          if (ck2xsy.body) {
            return of(ck2xsy.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ck2xsy());
  }
}
