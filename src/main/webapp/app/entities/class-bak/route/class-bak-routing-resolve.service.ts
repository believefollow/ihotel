import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IClassBak, ClassBak } from '../class-bak.model';
import { ClassBakService } from '../service/class-bak.service';

@Injectable({ providedIn: 'root' })
export class ClassBakRoutingResolveService implements Resolve<IClassBak> {
  constructor(protected service: ClassBakService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClassBak> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((classBak: HttpResponse<ClassBak>) => {
          if (classBak.body) {
            return of(classBak.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ClassBak());
  }
}
