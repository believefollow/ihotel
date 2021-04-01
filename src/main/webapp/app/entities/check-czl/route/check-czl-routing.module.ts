import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CheckCzlComponent } from '../list/check-czl.component';
import { CheckCzlDetailComponent } from '../detail/check-czl-detail.component';
import { CheckCzlUpdateComponent } from '../update/check-czl-update.component';
import { CheckCzlRoutingResolveService } from './check-czl-routing-resolve.service';

const checkCzlRoute: Routes = [
  {
    path: '',
    component: CheckCzlComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CheckCzlDetailComponent,
    resolve: {
      checkCzl: CheckCzlRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CheckCzlUpdateComponent,
    resolve: {
      checkCzl: CheckCzlRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CheckCzlUpdateComponent,
    resolve: {
      checkCzl: CheckCzlRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(checkCzlRoute)],
  exports: [RouterModule],
})
export class CheckCzlRoutingModule {}
