import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDGoods, DGoods } from '../d-goods.model';
import { DGoodsService } from '../service/d-goods.service';

@Injectable({ providedIn: 'root' })
export class DGoodsRoutingResolveService implements Resolve<IDGoods> {
  constructor(protected service: DGoodsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDGoods> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dGoods: HttpResponse<DGoods>) => {
          if (dGoods.body) {
            return of(dGoods.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DGoods());
  }
}
