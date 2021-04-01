import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDSpcz, DSpcz } from '../d-spcz.model';
import { DSpczService } from '../service/d-spcz.service';

@Injectable({ providedIn: 'root' })
export class DSpczRoutingResolveService implements Resolve<IDSpcz> {
  constructor(protected service: DSpczService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDSpcz> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dSpcz: HttpResponse<DSpcz>) => {
          if (dSpcz.body) {
            return of(dSpcz.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DSpcz());
  }
}
