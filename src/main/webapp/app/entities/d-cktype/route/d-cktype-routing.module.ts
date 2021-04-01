import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DCktypeComponent } from '../list/d-cktype.component';
import { DCktypeDetailComponent } from '../detail/d-cktype-detail.component';
import { DCktypeUpdateComponent } from '../update/d-cktype-update.component';
import { DCktypeRoutingResolveService } from './d-cktype-routing-resolve.service';

const dCktypeRoute: Routes = [
  {
    path: '',
    component: DCktypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DCktypeDetailComponent,
    resolve: {
      dCktype: DCktypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DCktypeUpdateComponent,
    resolve: {
      dCktype: DCktypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DCktypeUpdateComponent,
    resolve: {
      dCktype: DCktypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dCktypeRoute)],
  exports: [RouterModule],
})
export class DCktypeRoutingModule {}
