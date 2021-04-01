import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DTypeComponent } from '../list/d-type.component';
import { DTypeDetailComponent } from '../detail/d-type-detail.component';
import { DTypeUpdateComponent } from '../update/d-type-update.component';
import { DTypeRoutingResolveService } from './d-type-routing-resolve.service';

const dTypeRoute: Routes = [
  {
    path: '',
    component: DTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DTypeDetailComponent,
    resolve: {
      dType: DTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DTypeUpdateComponent,
    resolve: {
      dType: DTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DTypeUpdateComponent,
    resolve: {
      dType: DTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dTypeRoute)],
  exports: [RouterModule],
})
export class DTypeRoutingModule {}
