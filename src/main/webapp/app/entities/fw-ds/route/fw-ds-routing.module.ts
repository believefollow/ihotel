import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FwDsComponent } from '../list/fw-ds.component';
import { FwDsDetailComponent } from '../detail/fw-ds-detail.component';
import { FwDsUpdateComponent } from '../update/fw-ds-update.component';
import { FwDsRoutingResolveService } from './fw-ds-routing-resolve.service';

const fwDsRoute: Routes = [
  {
    path: '',
    component: FwDsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FwDsDetailComponent,
    resolve: {
      fwDs: FwDsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FwDsUpdateComponent,
    resolve: {
      fwDs: FwDsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FwDsUpdateComponent,
    resolve: {
      fwDs: FwDsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fwDsRoute)],
  exports: [RouterModule],
})
export class FwDsRoutingModule {}
