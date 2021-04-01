import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CheckinComponent } from '../list/checkin.component';
import { CheckinDetailComponent } from '../detail/checkin-detail.component';
import { CheckinUpdateComponent } from '../update/checkin-update.component';
import { CheckinRoutingResolveService } from './checkin-routing-resolve.service';

const checkinRoute: Routes = [
  {
    path: '',
    component: CheckinComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CheckinDetailComponent,
    resolve: {
      checkin: CheckinRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CheckinUpdateComponent,
    resolve: {
      checkin: CheckinRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CheckinUpdateComponent,
    resolve: {
      checkin: CheckinRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(checkinRoute)],
  exports: [RouterModule],
})
export class CheckinRoutingModule {}
