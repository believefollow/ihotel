import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFtXz, FtXz } from '../ft-xz.model';
import { FtXzService } from '../service/ft-xz.service';

@Injectable({ providedIn: 'root' })
export class FtXzRoutingResolveService implements Resolve<IFtXz> {
  constructor(protected service: FtXzService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFtXz> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ftXz: HttpResponse<FtXz>) => {
          if (ftXz.body) {
            return of(ftXz.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FtXz());
  }
}
