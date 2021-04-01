import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FwYlwpComponent } from '../list/fw-ylwp.component';
import { FwYlwpDetailComponent } from '../detail/fw-ylwp-detail.component';
import { FwYlwpUpdateComponent } from '../update/fw-ylwp-update.component';
import { FwYlwpRoutingResolveService } from './fw-ylwp-routing-resolve.service';

const fwYlwpRoute: Routes = [
  {
    path: '',
    component: FwYlwpComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FwYlwpDetailComponent,
    resolve: {
      fwYlwp: FwYlwpRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FwYlwpUpdateComponent,
    resolve: {
      fwYlwp: FwYlwpRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FwYlwpUpdateComponent,
    resolve: {
      fwYlwp: FwYlwpRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fwYlwpRoute)],
  exports: [RouterModule],
})
export class FwYlwpRoutingModule {}
