import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ClogComponent } from '../list/clog.component';
import { ClogDetailComponent } from '../detail/clog-detail.component';
import { ClogUpdateComponent } from '../update/clog-update.component';
import { ClogRoutingResolveService } from './clog-routing-resolve.service';

const clogRoute: Routes = [
  {
    path: '',
    component: ClogComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClogDetailComponent,
    resolve: {
      clog: ClogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClogUpdateComponent,
    resolve: {
      clog: ClogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClogUpdateComponent,
    resolve: {
      clog: ClogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(clogRoute)],
  exports: [RouterModule],
})
export class ClogRoutingModule {}
