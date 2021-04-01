import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FtXzComponent } from '../list/ft-xz.component';
import { FtXzDetailComponent } from '../detail/ft-xz-detail.component';
import { FtXzUpdateComponent } from '../update/ft-xz-update.component';
import { FtXzRoutingResolveService } from './ft-xz-routing-resolve.service';

const ftXzRoute: Routes = [
  {
    path: '',
    component: FtXzComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FtXzDetailComponent,
    resolve: {
      ftXz: FtXzRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FtXzUpdateComponent,
    resolve: {
      ftXz: FtXzRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FtXzUpdateComponent,
    resolve: {
      ftXz: FtXzRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ftXzRoute)],
  exports: [RouterModule],
})
export class FtXzRoutingModule {}
