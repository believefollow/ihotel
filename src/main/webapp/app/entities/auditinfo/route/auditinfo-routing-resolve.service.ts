import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAuditinfo, Auditinfo } from '../auditinfo.model';
import { AuditinfoService } from '../service/auditinfo.service';

@Injectable({ providedIn: 'root' })
export class AuditinfoRoutingResolveService implements Resolve<IAuditinfo> {
  constructor(protected service: AuditinfoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAuditinfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((auditinfo: HttpResponse<Auditinfo>) => {
          if (auditinfo.body) {
            return of(auditinfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Auditinfo());
  }
}
