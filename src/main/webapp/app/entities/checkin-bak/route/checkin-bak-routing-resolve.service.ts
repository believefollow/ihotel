import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICheckinBak, CheckinBak } from '../checkin-bak.model';
import { CheckinBakService } from '../service/checkin-bak.service';

@Injectable({ providedIn: 'root' })
export class CheckinBakRoutingResolveService implements Resolve<ICheckinBak> {
  constructor(protected service: CheckinBakService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICheckinBak> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((checkinBak: HttpResponse<CheckinBak>) => {
          if (checkinBak.body) {
            return of(checkinBak.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CheckinBak());
  }
}
