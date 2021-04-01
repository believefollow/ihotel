import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CrinfoComponent } from '../list/crinfo.component';
import { CrinfoDetailComponent } from '../detail/crinfo-detail.component';
import { CrinfoUpdateComponent } from '../update/crinfo-update.component';
import { CrinfoRoutingResolveService } from './crinfo-routing-resolve.service';

const crinfoRoute: Routes = [
  {
    path: '',
    component: CrinfoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CrinfoDetailComponent,
    resolve: {
      crinfo: CrinfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CrinfoUpdateComponent,
    resolve: {
      crinfo: CrinfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CrinfoUpdateComponent,
    resolve: {
      crinfo: CrinfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(crinfoRoute)],
  exports: [RouterModule],
})
export class CrinfoRoutingModule {}
