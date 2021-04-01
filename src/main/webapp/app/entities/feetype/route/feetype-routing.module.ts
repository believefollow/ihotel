import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FeetypeComponent } from '../list/feetype.component';
import { FeetypeDetailComponent } from '../detail/feetype-detail.component';
import { FeetypeUpdateComponent } from '../update/feetype-update.component';
import { FeetypeRoutingResolveService } from './feetype-routing-resolve.service';

const feetypeRoute: Routes = [
  {
    path: '',
    component: FeetypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FeetypeDetailComponent,
    resolve: {
      feetype: FeetypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FeetypeUpdateComponent,
    resolve: {
      feetype: FeetypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FeetypeUpdateComponent,
    resolve: {
      feetype: FeetypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(feetypeRoute)],
  exports: [RouterModule],
})
export class FeetypeRoutingModule {}
