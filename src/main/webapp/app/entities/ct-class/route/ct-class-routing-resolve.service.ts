import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICtClass, CtClass } from '../ct-class.model';
import { CtClassService } from '../service/ct-class.service';

@Injectable({ providedIn: 'root' })
export class CtClassRoutingResolveService implements Resolve<ICtClass> {
  constructor(protected service: CtClassService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICtClass> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ctClass: HttpResponse<CtClass>) => {
          if (ctClass.body) {
            return of(ctClass.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CtClass());
  }
}
