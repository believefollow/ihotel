import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IClassreport, Classreport } from '../classreport.model';
import { ClassreportService } from '../service/classreport.service';

@Injectable({ providedIn: 'root' })
export class ClassreportRoutingResolveService implements Resolve<IClassreport> {
  constructor(protected service: ClassreportService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClassreport> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((classreport: HttpResponse<Classreport>) => {
          if (classreport.body) {
            return of(classreport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Classreport());
  }
}
