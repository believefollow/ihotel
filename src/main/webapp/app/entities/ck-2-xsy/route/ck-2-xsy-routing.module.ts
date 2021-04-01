import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { Ck2xsyComponent } from '../list/ck-2-xsy.component';
import { Ck2xsyDetailComponent } from '../detail/ck-2-xsy-detail.component';
import { Ck2xsyUpdateComponent } from '../update/ck-2-xsy-update.component';
import { Ck2xsyRoutingResolveService } from './ck-2-xsy-routing-resolve.service';

const ck2xsyRoute: Routes = [
  {
    path: '',
    component: Ck2xsyComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: Ck2xsyDetailComponent,
    resolve: {
      ck2xsy: Ck2xsyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: Ck2xsyUpdateComponent,
    resolve: {
      ck2xsy: Ck2xsyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: Ck2xsyUpdateComponent,
    resolve: {
      ck2xsy: Ck2xsyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ck2xsyRoute)],
  exports: [RouterModule],
})
export class Ck2xsyRoutingModule {}
