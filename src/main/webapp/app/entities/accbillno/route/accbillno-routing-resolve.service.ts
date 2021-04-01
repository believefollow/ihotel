import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAccbillno, Accbillno } from '../accbillno.model';
import { AccbillnoService } from '../service/accbillno.service';

@Injectable({ providedIn: 'root' })
export class AccbillnoRoutingResolveService implements Resolve<IAccbillno> {
  constructor(protected service: AccbillnoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccbillno> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((accbillno: HttpResponse<Accbillno>) => {
          if (accbillno.body) {
            return of(accbillno.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Accbillno());
  }
}
