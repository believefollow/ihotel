import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICrinfo, Crinfo } from '../crinfo.model';
import { CrinfoService } from '../service/crinfo.service';

@Injectable({ providedIn: 'root' })
export class CrinfoRoutingResolveService implements Resolve<ICrinfo> {
  constructor(protected service: CrinfoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICrinfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((crinfo: HttpResponse<Crinfo>) => {
          if (crinfo.body) {
            return of(crinfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Crinfo());
  }
}
