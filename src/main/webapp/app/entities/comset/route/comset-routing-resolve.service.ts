import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IComset, Comset } from '../comset.model';
import { ComsetService } from '../service/comset.service';

@Injectable({ providedIn: 'root' })
export class ComsetRoutingResolveService implements Resolve<IComset> {
  constructor(protected service: ComsetService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IComset> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((comset: HttpResponse<Comset>) => {
          if (comset.body) {
            return of(comset.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Comset());
  }
}
