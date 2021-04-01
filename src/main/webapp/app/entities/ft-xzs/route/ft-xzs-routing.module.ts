import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FtXzsComponent } from '../list/ft-xzs.component';
import { FtXzsDetailComponent } from '../detail/ft-xzs-detail.component';
import { FtXzsUpdateComponent } from '../update/ft-xzs-update.component';
import { FtXzsRoutingResolveService } from './ft-xzs-routing-resolve.service';

const ftXzsRoute: Routes = [
  {
    path: '',
    component: FtXzsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FtXzsDetailComponent,
    resolve: {
      ftXzs: FtXzsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FtXzsUpdateComponent,
    resolve: {
      ftXzs: FtXzsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FtXzsUpdateComponent,
    resolve: {
      ftXzs: FtXzsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ftXzsRoute)],
  exports: [RouterModule],
})
export class FtXzsRoutingModule {}
