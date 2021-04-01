import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AccComponent } from '../list/acc.component';
import { AccDetailComponent } from '../detail/acc-detail.component';
import { AccUpdateComponent } from '../update/acc-update.component';
import { AccRoutingResolveService } from './acc-routing-resolve.service';

const accRoute: Routes = [
  {
    path: '',
    component: AccComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AccDetailComponent,
    resolve: {
      acc: AccRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccUpdateComponent,
    resolve: {
      acc: AccRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AccUpdateComponent,
    resolve: {
      acc: AccRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(accRoute)],
  exports: [RouterModule],
})
export class AccRoutingModule {}
