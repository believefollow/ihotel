import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DCktimeComponent } from '../list/d-cktime.component';
import { DCktimeDetailComponent } from '../detail/d-cktime-detail.component';
import { DCktimeUpdateComponent } from '../update/d-cktime-update.component';
import { DCktimeRoutingResolveService } from './d-cktime-routing-resolve.service';

const dCktimeRoute: Routes = [
  {
    path: '',
    component: DCktimeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DCktimeDetailComponent,
    resolve: {
      dCktime: DCktimeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DCktimeUpdateComponent,
    resolve: {
      dCktime: DCktimeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DCktimeUpdateComponent,
    resolve: {
      dCktime: DCktimeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dCktimeRoute)],
  exports: [RouterModule],
})
export class DCktimeRoutingModule {}
