import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AccPpComponent } from '../list/acc-pp.component';
import { AccPpDetailComponent } from '../detail/acc-pp-detail.component';
import { AccPpUpdateComponent } from '../update/acc-pp-update.component';
import { AccPpRoutingResolveService } from './acc-pp-routing-resolve.service';

const accPpRoute: Routes = [
  {
    path: '',
    component: AccPpComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AccPpDetailComponent,
    resolve: {
      accPp: AccPpRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccPpUpdateComponent,
    resolve: {
      accPp: AccPpRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AccPpUpdateComponent,
    resolve: {
      accPp: AccPpRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(accPpRoute)],
  exports: [RouterModule],
})
export class AccPpRoutingModule {}
