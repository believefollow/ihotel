import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AdhocComponent } from '../list/adhoc.component';
import { AdhocDetailComponent } from '../detail/adhoc-detail.component';
import { AdhocUpdateComponent } from '../update/adhoc-update.component';
import { AdhocRoutingResolveService } from './adhoc-routing-resolve.service';

const adhocRoute: Routes = [
  {
    path: '',
    component: AdhocComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdhocDetailComponent,
    resolve: {
      adhoc: AdhocRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdhocUpdateComponent,
    resolve: {
      adhoc: AdhocRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdhocUpdateComponent,
    resolve: {
      adhoc: AdhocRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(adhocRoute)],
  exports: [RouterModule],
})
export class AdhocRoutingModule {}
