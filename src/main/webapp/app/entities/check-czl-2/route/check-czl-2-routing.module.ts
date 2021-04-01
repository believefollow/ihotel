import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CheckCzl2Component } from '../list/check-czl-2.component';
import { CheckCzl2DetailComponent } from '../detail/check-czl-2-detail.component';
import { CheckCzl2UpdateComponent } from '../update/check-czl-2-update.component';
import { CheckCzl2RoutingResolveService } from './check-czl-2-routing-resolve.service';

const checkCzl2Route: Routes = [
  {
    path: '',
    component: CheckCzl2Component,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CheckCzl2DetailComponent,
    resolve: {
      checkCzl2: CheckCzl2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CheckCzl2UpdateComponent,
    resolve: {
      checkCzl2: CheckCzl2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CheckCzl2UpdateComponent,
    resolve: {
      checkCzl2: CheckCzl2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(checkCzl2Route)],
  exports: [RouterModule],
})
export class CheckCzl2RoutingModule {}
