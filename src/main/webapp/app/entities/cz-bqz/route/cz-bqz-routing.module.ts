import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CzBqzComponent } from '../list/cz-bqz.component';
import { CzBqzDetailComponent } from '../detail/cz-bqz-detail.component';
import { CzBqzUpdateComponent } from '../update/cz-bqz-update.component';
import { CzBqzRoutingResolveService } from './cz-bqz-routing-resolve.service';

const czBqzRoute: Routes = [
  {
    path: '',
    component: CzBqzComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CzBqzDetailComponent,
    resolve: {
      czBqz: CzBqzRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CzBqzUpdateComponent,
    resolve: {
      czBqz: CzBqzRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CzBqzUpdateComponent,
    resolve: {
      czBqz: CzBqzRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(czBqzRoute)],
  exports: [RouterModule],
})
export class CzBqzRoutingModule {}
