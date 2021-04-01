import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICode1, Code1 } from '../code-1.model';
import { Code1Service } from '../service/code-1.service';

@Injectable({ providedIn: 'root' })
export class Code1RoutingResolveService implements Resolve<ICode1> {
  constructor(protected service: Code1Service, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICode1> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((code1: HttpResponse<Code1>) => {
          if (code1.body) {
            return of(code1.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Code1());
  }
}
