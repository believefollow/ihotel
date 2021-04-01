import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FwJywpComponent } from '../list/fw-jywp.component';
import { FwJywpDetailComponent } from '../detail/fw-jywp-detail.component';
import { FwJywpUpdateComponent } from '../update/fw-jywp-update.component';
import { FwJywpRoutingResolveService } from './fw-jywp-routing-resolve.service';

const fwJywpRoute: Routes = [
  {
    path: '',
    component: FwJywpComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FwJywpDetailComponent,
    resolve: {
      fwJywp: FwJywpRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FwJywpUpdateComponent,
    resolve: {
      fwJywp: FwJywpRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FwJywpUpdateComponent,
    resolve: {
      fwJywp: FwJywpRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fwJywpRoute)],
  exports: [RouterModule],
})
export class FwJywpRoutingModule {}
