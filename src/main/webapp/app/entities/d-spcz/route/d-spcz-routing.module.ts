import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DSpczComponent } from '../list/d-spcz.component';
import { DSpczDetailComponent } from '../detail/d-spcz-detail.component';
import { DSpczUpdateComponent } from '../update/d-spcz-update.component';
import { DSpczRoutingResolveService } from './d-spcz-routing-resolve.service';

const dSpczRoute: Routes = [
  {
    path: '',
    component: DSpczComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DSpczDetailComponent,
    resolve: {
      dSpcz: DSpczRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DSpczUpdateComponent,
    resolve: {
      dSpcz: DSpczRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DSpczUpdateComponent,
    resolve: {
      dSpcz: DSpczRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dSpczRoute)],
  exports: [RouterModule],
})
export class DSpczRoutingModule {}
