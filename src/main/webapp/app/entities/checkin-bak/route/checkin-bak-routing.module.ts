import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CheckinBakComponent } from '../list/checkin-bak.component';
import { CheckinBakDetailComponent } from '../detail/checkin-bak-detail.component';
import { CheckinBakUpdateComponent } from '../update/checkin-bak-update.component';
import { CheckinBakRoutingResolveService } from './checkin-bak-routing-resolve.service';

const checkinBakRoute: Routes = [
  {
    path: '',
    component: CheckinBakComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CheckinBakDetailComponent,
    resolve: {
      checkinBak: CheckinBakRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CheckinBakUpdateComponent,
    resolve: {
      checkinBak: CheckinBakRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CheckinBakUpdateComponent,
    resolve: {
      checkinBak: CheckinBakRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(checkinBakRoute)],
  exports: [RouterModule],
})
export class CheckinBakRoutingModule {}
