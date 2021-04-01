import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CzlCzComponent } from '../list/czl-cz.component';
import { CzlCzDetailComponent } from '../detail/czl-cz-detail.component';
import { CzlCzUpdateComponent } from '../update/czl-cz-update.component';
import { CzlCzRoutingResolveService } from './czl-cz-routing-resolve.service';

const czlCzRoute: Routes = [
  {
    path: '',
    component: CzlCzComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CzlCzDetailComponent,
    resolve: {
      czlCz: CzlCzRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CzlCzUpdateComponent,
    resolve: {
      czlCz: CzlCzRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CzlCzUpdateComponent,
    resolve: {
      czlCz: CzlCzRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(czlCzRoute)],
  exports: [RouterModule],
})
export class CzlCzRoutingModule {}
