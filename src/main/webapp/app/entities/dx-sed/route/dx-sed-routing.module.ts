import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DxSedComponent } from '../list/dx-sed.component';
import { DxSedDetailComponent } from '../detail/dx-sed-detail.component';
import { DxSedUpdateComponent } from '../update/dx-sed-update.component';
import { DxSedRoutingResolveService } from './dx-sed-routing-resolve.service';

const dxSedRoute: Routes = [
  {
    path: '',
    component: DxSedComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DxSedDetailComponent,
    resolve: {
      dxSed: DxSedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DxSedUpdateComponent,
    resolve: {
      dxSed: DxSedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DxSedUpdateComponent,
    resolve: {
      dxSed: DxSedRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dxSedRoute)],
  exports: [RouterModule],
})
export class DxSedRoutingModule {}
