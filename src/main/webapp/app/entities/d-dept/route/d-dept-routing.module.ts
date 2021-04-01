import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DDeptComponent } from '../list/d-dept.component';
import { DDeptDetailComponent } from '../detail/d-dept-detail.component';
import { DDeptUpdateComponent } from '../update/d-dept-update.component';
import { DDeptRoutingResolveService } from './d-dept-routing-resolve.service';

const dDeptRoute: Routes = [
  {
    path: '',
    component: DDeptComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DDeptDetailComponent,
    resolve: {
      dDept: DDeptRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DDeptUpdateComponent,
    resolve: {
      dDept: DDeptRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DDeptUpdateComponent,
    resolve: {
      dDept: DDeptRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dDeptRoute)],
  exports: [RouterModule],
})
export class DDeptRoutingModule {}
