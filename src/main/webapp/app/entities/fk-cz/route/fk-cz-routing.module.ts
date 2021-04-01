import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FkCzComponent } from '../list/fk-cz.component';
import { FkCzDetailComponent } from '../detail/fk-cz-detail.component';
import { FkCzUpdateComponent } from '../update/fk-cz-update.component';
import { FkCzRoutingResolveService } from './fk-cz-routing-resolve.service';

const fkCzRoute: Routes = [
  {
    path: '',
    component: FkCzComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FkCzDetailComponent,
    resolve: {
      fkCz: FkCzRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FkCzUpdateComponent,
    resolve: {
      fkCz: FkCzRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FkCzUpdateComponent,
    resolve: {
      fkCz: FkCzRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fkCzRoute)],
  exports: [RouterModule],
})
export class FkCzRoutingModule {}
