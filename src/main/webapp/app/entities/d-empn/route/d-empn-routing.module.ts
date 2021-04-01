import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DEmpnComponent } from '../list/d-empn.component';
import { DEmpnDetailComponent } from '../detail/d-empn-detail.component';
import { DEmpnUpdateComponent } from '../update/d-empn-update.component';
import { DEmpnRoutingResolveService } from './d-empn-routing-resolve.service';

const dEmpnRoute: Routes = [
  {
    path: '',
    component: DEmpnComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DEmpnDetailComponent,
    resolve: {
      dEmpn: DEmpnRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DEmpnUpdateComponent,
    resolve: {
      dEmpn: DEmpnRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DEmpnUpdateComponent,
    resolve: {
      dEmpn: DEmpnRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dEmpnRoute)],
  exports: [RouterModule],
})
export class DEmpnRoutingModule {}
