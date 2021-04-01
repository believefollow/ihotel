import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DDbComponent } from '../list/d-db.component';
import { DDbDetailComponent } from '../detail/d-db-detail.component';
import { DDbUpdateComponent } from '../update/d-db-update.component';
import { DDbRoutingResolveService } from './d-db-routing-resolve.service';

const dDbRoute: Routes = [
  {
    path: '',
    component: DDbComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DDbDetailComponent,
    resolve: {
      dDb: DDbRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DDbUpdateComponent,
    resolve: {
      dDb: DDbRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DDbUpdateComponent,
    resolve: {
      dDb: DDbRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dDbRoute)],
  exports: [RouterModule],
})
export class DDbRoutingModule {}
