import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDxSedinfo, DxSedinfo } from '../dx-sedinfo.model';
import { DxSedinfoService } from '../service/dx-sedinfo.service';

@Injectable({ providedIn: 'root' })
export class DxSedinfoRoutingResolveService implements Resolve<IDxSedinfo> {
  constructor(protected service: DxSedinfoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDxSedinfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dxSedinfo: HttpResponse<DxSedinfo>) => {
          if (dxSedinfo.body) {
            return of(dxSedinfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DxSedinfo());
  }
}
