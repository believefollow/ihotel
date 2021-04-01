import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FwWxfComponent } from '../list/fw-wxf.component';
import { FwWxfDetailComponent } from '../detail/fw-wxf-detail.component';
import { FwWxfUpdateComponent } from '../update/fw-wxf-update.component';
import { FwWxfRoutingResolveService } from './fw-wxf-routing-resolve.service';

const fwWxfRoute: Routes = [
  {
    path: '',
    component: FwWxfComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FwWxfDetailComponent,
    resolve: {
      fwWxf: FwWxfRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FwWxfUpdateComponent,
    resolve: {
      fwWxf: FwWxfRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FwWxfUpdateComponent,
    resolve: {
      fwWxf: FwWxfRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fwWxfRoute)],
  exports: [RouterModule],
})
export class FwWxfRoutingModule {}
