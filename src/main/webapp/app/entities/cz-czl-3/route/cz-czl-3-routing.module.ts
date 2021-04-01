import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CzCzl3Component } from '../list/cz-czl-3.component';
import { CzCzl3DetailComponent } from '../detail/cz-czl-3-detail.component';
import { CzCzl3UpdateComponent } from '../update/cz-czl-3-update.component';
import { CzCzl3RoutingResolveService } from './cz-czl-3-routing-resolve.service';

const czCzl3Route: Routes = [
  {
    path: '',
    component: CzCzl3Component,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CzCzl3DetailComponent,
    resolve: {
      czCzl3: CzCzl3RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CzCzl3UpdateComponent,
    resolve: {
      czCzl3: CzCzl3RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CzCzl3UpdateComponent,
    resolve: {
      czCzl3: CzCzl3RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(czCzl3Route)],
  exports: [RouterModule],
})
export class CzCzl3RoutingModule {}
