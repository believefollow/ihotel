import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ComsetComponent } from '../list/comset.component';
import { ComsetDetailComponent } from '../detail/comset-detail.component';
import { ComsetUpdateComponent } from '../update/comset-update.component';
import { ComsetRoutingResolveService } from './comset-routing-resolve.service';

const comsetRoute: Routes = [
  {
    path: '',
    component: ComsetComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ComsetDetailComponent,
    resolve: {
      comset: ComsetRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ComsetUpdateComponent,
    resolve: {
      comset: ComsetRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ComsetUpdateComponent,
    resolve: {
      comset: ComsetRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(comsetRoute)],
  exports: [RouterModule],
})
export class ComsetRoutingModule {}
