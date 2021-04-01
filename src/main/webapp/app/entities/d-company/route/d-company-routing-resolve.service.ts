import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDCompany, DCompany } from '../d-company.model';
import { DCompanyService } from '../service/d-company.service';

@Injectable({ providedIn: 'root' })
export class DCompanyRoutingResolveService implements Resolve<IDCompany> {
  constructor(protected service: DCompanyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDCompany> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dCompany: HttpResponse<DCompany>) => {
          if (dCompany.body) {
            return of(dCompany.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DCompany());
  }
}
