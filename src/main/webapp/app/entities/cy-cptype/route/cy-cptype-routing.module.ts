import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CyCptypeComponent } from '../list/cy-cptype.component';
import { CyCptypeDetailComponent } from '../detail/cy-cptype-detail.component';
import { CyCptypeUpdateComponent } from '../update/cy-cptype-update.component';
import { CyCptypeRoutingResolveService } from './cy-cptype-routing-resolve.service';

const cyCptypeRoute: Routes = [
  {
    path: '',
    component: CyCptypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CyCptypeDetailComponent,
    resolve: {
      cyCptype: CyCptypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CyCptypeUpdateComponent,
    resolve: {
      cyCptype: CyCptypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CyCptypeUpdateComponent,
    resolve: {
      cyCptype: CyCptypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cyCptypeRoute)],
  exports: [RouterModule],
})
export class CyCptypeRoutingModule {}
