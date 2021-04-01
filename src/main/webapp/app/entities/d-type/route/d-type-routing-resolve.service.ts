import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDType, DType } from '../d-type.model';
import { DTypeService } from '../service/d-type.service';

@Injectable({ providedIn: 'root' })
export class DTypeRoutingResolveService implements Resolve<IDType> {
  constructor(protected service: DTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dType: HttpResponse<DType>) => {
          if (dType.body) {
            return of(dType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DType());
  }
}
