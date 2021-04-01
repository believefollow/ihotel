import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CheckinTzComponent } from '../list/checkin-tz.component';
import { CheckinTzDetailComponent } from '../detail/checkin-tz-detail.component';
import { CheckinTzUpdateComponent } from '../update/checkin-tz-update.component';
import { CheckinTzRoutingResolveService } from './checkin-tz-routing-resolve.service';

const checkinTzRoute: Routes = [
  {
    path: '',
    component: CheckinTzComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CheckinTzDetailComponent,
    resolve: {
      checkinTz: CheckinTzRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CheckinTzUpdateComponent,
    resolve: {
      checkinTz: CheckinTzRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CheckinTzUpdateComponent,
    resolve: {
      checkinTz: CheckinTzRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(checkinTzRoute)],
  exports: [RouterModule],
})
export class CheckinTzRoutingModule {}
