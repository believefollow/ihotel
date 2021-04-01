import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DXsComponent } from '../list/d-xs.component';
import { DXsDetailComponent } from '../detail/d-xs-detail.component';
import { DXsUpdateComponent } from '../update/d-xs-update.component';
import { DXsRoutingResolveService } from './d-xs-routing-resolve.service';

const dXsRoute: Routes = [
  {
    path: '',
    component: DXsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DXsDetailComponent,
    resolve: {
      dXs: DXsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DXsUpdateComponent,
    resolve: {
      dXs: DXsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DXsUpdateComponent,
    resolve: {
      dXs: DXsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dXsRoute)],
  exports: [RouterModule],
})
export class DXsRoutingModule {}
