import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DPdbComponent } from '../list/d-pdb.component';
import { DPdbDetailComponent } from '../detail/d-pdb-detail.component';
import { DPdbUpdateComponent } from '../update/d-pdb-update.component';
import { DPdbRoutingResolveService } from './d-pdb-routing-resolve.service';

const dPdbRoute: Routes = [
  {
    path: '',
    component: DPdbComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DPdbDetailComponent,
    resolve: {
      dPdb: DPdbRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DPdbUpdateComponent,
    resolve: {
      dPdb: DPdbRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DPdbUpdateComponent,
    resolve: {
      dPdb: DPdbRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dPdbRoute)],
  exports: [RouterModule],
})
export class DPdbRoutingModule {}
