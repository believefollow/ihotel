import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFtXzs, FtXzs } from '../ft-xzs.model';
import { FtXzsService } from '../service/ft-xzs.service';

@Injectable({ providedIn: 'root' })
export class FtXzsRoutingResolveService implements Resolve<IFtXzs> {
  constructor(protected service: FtXzsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFtXzs> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ftXzs: HttpResponse<FtXzs>) => {
          if (ftXzs.body) {
            return of(ftXzs.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FtXzs());
  }
}
