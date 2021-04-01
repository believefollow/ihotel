import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICyCptype, CyCptype } from '../cy-cptype.model';
import { CyCptypeService } from '../service/cy-cptype.service';

@Injectable({ providedIn: 'root' })
export class CyCptypeRoutingResolveService implements Resolve<ICyCptype> {
  constructor(protected service: CyCptypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICyCptype> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cyCptype: HttpResponse<CyCptype>) => {
          if (cyCptype.body) {
            return of(cyCptype.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CyCptype());
  }
}
