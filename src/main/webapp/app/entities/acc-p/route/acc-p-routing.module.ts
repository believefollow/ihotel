import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AccPComponent } from '../list/acc-p.component';
import { AccPDetailComponent } from '../detail/acc-p-detail.component';
import { AccPUpdateComponent } from '../update/acc-p-update.component';
import { AccPRoutingResolveService } from './acc-p-routing-resolve.service';

const accPRoute: Routes = [
  {
    path: '',
    component: AccPComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AccPDetailComponent,
    resolve: {
      accP: AccPRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccPUpdateComponent,
    resolve: {
      accP: AccPRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AccPUpdateComponent,
    resolve: {
      accP: AccPRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(accPRoute)],
  exports: [RouterModule],
})
export class AccPRoutingModule {}
