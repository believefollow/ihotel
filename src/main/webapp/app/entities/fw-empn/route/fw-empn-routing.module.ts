import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FwEmpnComponent } from '../list/fw-empn.component';
import { FwEmpnDetailComponent } from '../detail/fw-empn-detail.component';
import { FwEmpnUpdateComponent } from '../update/fw-empn-update.component';
import { FwEmpnRoutingResolveService } from './fw-empn-routing-resolve.service';

const fwEmpnRoute: Routes = [
  {
    path: '',
    component: FwEmpnComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FwEmpnDetailComponent,
    resolve: {
      fwEmpn: FwEmpnRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FwEmpnUpdateComponent,
    resolve: {
      fwEmpn: FwEmpnRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FwEmpnUpdateComponent,
    resolve: {
      fwEmpn: FwEmpnRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fwEmpnRoute)],
  exports: [RouterModule],
})
export class FwEmpnRoutingModule {}
