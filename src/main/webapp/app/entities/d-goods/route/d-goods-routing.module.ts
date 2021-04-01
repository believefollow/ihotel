import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DGoodsComponent } from '../list/d-goods.component';
import { DGoodsDetailComponent } from '../detail/d-goods-detail.component';
import { DGoodsUpdateComponent } from '../update/d-goods-update.component';
import { DGoodsRoutingResolveService } from './d-goods-routing-resolve.service';

const dGoodsRoute: Routes = [
  {
    path: '',
    component: DGoodsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DGoodsDetailComponent,
    resolve: {
      dGoods: DGoodsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DGoodsUpdateComponent,
    resolve: {
      dGoods: DGoodsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DGoodsUpdateComponent,
    resolve: {
      dGoods: DGoodsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dGoodsRoute)],
  exports: [RouterModule],
})
export class DGoodsRoutingModule {}
