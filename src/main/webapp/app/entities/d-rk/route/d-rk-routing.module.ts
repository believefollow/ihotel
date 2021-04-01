import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DRkComponent } from '../list/d-rk.component';
import { DRkDetailComponent } from '../detail/d-rk-detail.component';
import { DRkUpdateComponent } from '../update/d-rk-update.component';
import { DRkRoutingResolveService } from './d-rk-routing-resolve.service';

const dRkRoute: Routes = [
  {
    path: '',
    component: DRkComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DRkDetailComponent,
    resolve: {
      dRk: DRkRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DRkUpdateComponent,
    resolve: {
      dRk: DRkRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DRkUpdateComponent,
    resolve: {
      dRk: DRkRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dRkRoute)],
  exports: [RouterModule],
})
export class DRkRoutingModule {}
