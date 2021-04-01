import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDXs, DXs } from '../d-xs.model';
import { DXsService } from '../service/d-xs.service';

@Injectable({ providedIn: 'root' })
export class DXsRoutingResolveService implements Resolve<IDXs> {
  constructor(protected service: DXsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDXs> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dXs: HttpResponse<DXs>) => {
          if (dXs.body) {
            return of(dXs.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DXs());
  }
}
