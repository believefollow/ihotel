import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CheckinAccountComponent } from '../list/checkin-account.component';
import { CheckinAccountDetailComponent } from '../detail/checkin-account-detail.component';
import { CheckinAccountUpdateComponent } from '../update/checkin-account-update.component';
import { CheckinAccountRoutingResolveService } from './checkin-account-routing-resolve.service';

const checkinAccountRoute: Routes = [
  {
    path: '',
    component: CheckinAccountComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CheckinAccountDetailComponent,
    resolve: {
      checkinAccount: CheckinAccountRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CheckinAccountUpdateComponent,
    resolve: {
      checkinAccount: CheckinAccountRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CheckinAccountUpdateComponent,
    resolve: {
      checkinAccount: CheckinAccountRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(checkinAccountRoute)],
  exports: [RouterModule],
})
export class CheckinAccountRoutingModule {}
