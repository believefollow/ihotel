import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DxSedinfoComponent } from '../list/dx-sedinfo.component';
import { DxSedinfoDetailComponent } from '../detail/dx-sedinfo-detail.component';
import { DxSedinfoUpdateComponent } from '../update/dx-sedinfo-update.component';
import { DxSedinfoRoutingResolveService } from './dx-sedinfo-routing-resolve.service';

const dxSedinfoRoute: Routes = [
  {
    path: '',
    component: DxSedinfoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DxSedinfoDetailComponent,
    resolve: {
      dxSedinfo: DxSedinfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DxSedinfoUpdateComponent,
    resolve: {
      dxSedinfo: DxSedinfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DxSedinfoUpdateComponent,
    resolve: {
      dxSedinfo: DxSedinfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dxSedinfoRoute)],
  exports: [RouterModule],
})
export class DxSedinfoRoutingModule {}
