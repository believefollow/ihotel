import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EeComponent } from '../list/ee.component';
import { EeDetailComponent } from '../detail/ee-detail.component';
import { EeUpdateComponent } from '../update/ee-update.component';
import { EeRoutingResolveService } from './ee-routing-resolve.service';

const eeRoute: Routes = [
  {
    path: '',
    component: EeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EeDetailComponent,
    resolve: {
      ee: EeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EeUpdateComponent,
    resolve: {
      ee: EeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EeUpdateComponent,
    resolve: {
      ee: EeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eeRoute)],
  exports: [RouterModule],
})
export class EeRoutingModule {}
