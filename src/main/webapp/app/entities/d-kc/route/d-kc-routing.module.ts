import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DKcComponent } from '../list/d-kc.component';
import { DKcDetailComponent } from '../detail/d-kc-detail.component';
import { DKcUpdateComponent } from '../update/d-kc-update.component';
import { DKcRoutingResolveService } from './d-kc-routing-resolve.service';

const dKcRoute: Routes = [
  {
    path: '',
    component: DKcComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DKcDetailComponent,
    resolve: {
      dKc: DKcRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DKcUpdateComponent,
    resolve: {
      dKc: DKcRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DKcUpdateComponent,
    resolve: {
      dKc: DKcRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dKcRoute)],
  exports: [RouterModule],
})
export class DKcRoutingModule {}
