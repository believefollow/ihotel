import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICyRoomtype, CyRoomtype } from '../cy-roomtype.model';
import { CyRoomtypeService } from '../service/cy-roomtype.service';

@Injectable({ providedIn: 'root' })
export class CyRoomtypeRoutingResolveService implements Resolve<ICyRoomtype> {
  constructor(protected service: CyRoomtypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICyRoomtype> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cyRoomtype: HttpResponse<CyRoomtype>) => {
          if (cyRoomtype.body) {
            return of(cyRoomtype.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CyRoomtype());
  }
}
