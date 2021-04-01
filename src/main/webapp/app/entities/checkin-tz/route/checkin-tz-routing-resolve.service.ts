import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICheckinTz, CheckinTz } from '../checkin-tz.model';
import { CheckinTzService } from '../service/checkin-tz.service';

@Injectable({ providedIn: 'root' })
export class CheckinTzRoutingResolveService implements Resolve<ICheckinTz> {
  constructor(protected service: CheckinTzService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICheckinTz> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((checkinTz: HttpResponse<CheckinTz>) => {
          if (checkinTz.body) {
            return of(checkinTz.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CheckinTz());
  }
}
