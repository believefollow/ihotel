import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DUnitComponent } from '../list/d-unit.component';
import { DUnitDetailComponent } from '../detail/d-unit-detail.component';
import { DUnitUpdateComponent } from '../update/d-unit-update.component';
import { DUnitRoutingResolveService } from './d-unit-routing-resolve.service';

const dUnitRoute: Routes = [
  {
    path: '',
    component: DUnitComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DUnitDetailComponent,
    resolve: {
      dUnit: DUnitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DUnitUpdateComponent,
    resolve: {
      dUnit: DUnitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DUnitUpdateComponent,
    resolve: {
      dUnit: DUnitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dUnitRoute)],
  exports: [RouterModule],
})
export class DUnitRoutingModule {}
