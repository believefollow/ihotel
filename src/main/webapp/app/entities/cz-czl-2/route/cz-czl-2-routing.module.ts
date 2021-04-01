import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CzCzl2Component } from '../list/cz-czl-2.component';
import { CzCzl2DetailComponent } from '../detail/cz-czl-2-detail.component';
import { CzCzl2UpdateComponent } from '../update/cz-czl-2-update.component';
import { CzCzl2RoutingResolveService } from './cz-czl-2-routing-resolve.service';

const czCzl2Route: Routes = [
  {
    path: '',
    component: CzCzl2Component,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CzCzl2DetailComponent,
    resolve: {
      czCzl2: CzCzl2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CzCzl2UpdateComponent,
    resolve: {
      czCzl2: CzCzl2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CzCzl2UpdateComponent,
    resolve: {
      czCzl2: CzCzl2RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(czCzl2Route)],
  exports: [RouterModule],
})
export class CzCzl2RoutingModule {}
