import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DCkComponent } from '../list/d-ck.component';
import { DCkDetailComponent } from '../detail/d-ck-detail.component';
import { DCkUpdateComponent } from '../update/d-ck-update.component';
import { DCkRoutingResolveService } from './d-ck-routing-resolve.service';

const dCkRoute: Routes = [
  {
    path: '',
    component: DCkComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DCkDetailComponent,
    resolve: {
      dCk: DCkRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DCkUpdateComponent,
    resolve: {
      dCk: DCkRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DCkUpdateComponent,
    resolve: {
      dCk: DCkRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dCkRoute)],
  exports: [RouterModule],
})
export class DCkRoutingModule {}
