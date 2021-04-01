import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ErrlogComponent } from '../list/errlog.component';
import { ErrlogDetailComponent } from '../detail/errlog-detail.component';
import { ErrlogUpdateComponent } from '../update/errlog-update.component';
import { ErrlogRoutingResolveService } from './errlog-routing-resolve.service';

const errlogRoute: Routes = [
  {
    path: '',
    component: ErrlogComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ErrlogDetailComponent,
    resolve: {
      errlog: ErrlogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ErrlogUpdateComponent,
    resolve: {
      errlog: ErrlogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ErrlogUpdateComponent,
    resolve: {
      errlog: ErrlogRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(errlogRoute)],
  exports: [RouterModule],
})
export class ErrlogRoutingModule {}
