import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAccounts, Accounts } from '../accounts.model';
import { AccountsService } from '../service/accounts.service';

@Injectable({ providedIn: 'root' })
export class AccountsRoutingResolveService implements Resolve<IAccounts> {
  constructor(protected service: AccountsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccounts> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((accounts: HttpResponse<Accounts>) => {
          if (accounts.body) {
            return of(accounts.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Accounts());
  }
}
