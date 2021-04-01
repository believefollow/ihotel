import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICheckinAccount, CheckinAccount } from '../checkin-account.model';
import { CheckinAccountService } from '../service/checkin-account.service';

@Injectable({ providedIn: 'root' })
export class CheckinAccountRoutingResolveService implements Resolve<ICheckinAccount> {
  constructor(protected service: CheckinAccountService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICheckinAccount> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((checkinAccount: HttpResponse<CheckinAccount>) => {
          if (checkinAccount.body) {
            return of(checkinAccount.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CheckinAccount());
  }
}
