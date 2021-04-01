import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DDepotComponent } from '../list/d-depot.component';
import { DDepotDetailComponent } from '../detail/d-depot-detail.component';
import { DDepotUpdateComponent } from '../update/d-depot-update.component';
import { DDepotRoutingResolveService } from './d-depot-routing-resolve.service';

const dDepotRoute: Routes = [
  {
    path: '',
    component: DDepotComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DDepotDetailComponent,
    resolve: {
      dDepot: DDepotRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DDepotUpdateComponent,
    resolve: {
      dDepot: DDepotRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DDepotUpdateComponent,
    resolve: {
      dDepot: DDepotRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dDepotRoute)],
  exports: [RouterModule],
})
export class DDepotRoutingModule {}
