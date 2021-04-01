import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DayearndetailComponent } from '../list/dayearndetail.component';
import { DayearndetailDetailComponent } from '../detail/dayearndetail-detail.component';
import { DayearndetailUpdateComponent } from '../update/dayearndetail-update.component';
import { DayearndetailRoutingResolveService } from './dayearndetail-routing-resolve.service';

const dayearndetailRoute: Routes = [
  {
    path: '',
    component: DayearndetailComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DayearndetailDetailComponent,
    resolve: {
      dayearndetail: DayearndetailRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DayearndetailUpdateComponent,
    resolve: {
      dayearndetail: DayearndetailRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DayearndetailUpdateComponent,
    resolve: {
      dayearndetail: DayearndetailRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dayearndetailRoute)],
  exports: [RouterModule],
})
export class DayearndetailRoutingModule {}
