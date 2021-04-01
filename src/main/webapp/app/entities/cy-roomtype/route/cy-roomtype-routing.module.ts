import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CyRoomtypeComponent } from '../list/cy-roomtype.component';
import { CyRoomtypeDetailComponent } from '../detail/cy-roomtype-detail.component';
import { CyRoomtypeUpdateComponent } from '../update/cy-roomtype-update.component';
import { CyRoomtypeRoutingResolveService } from './cy-roomtype-routing-resolve.service';

const cyRoomtypeRoute: Routes = [
  {
    path: '',
    component: CyRoomtypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CyRoomtypeDetailComponent,
    resolve: {
      cyRoomtype: CyRoomtypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CyRoomtypeUpdateComponent,
    resolve: {
      cyRoomtype: CyRoomtypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CyRoomtypeUpdateComponent,
    resolve: {
      cyRoomtype: CyRoomtypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cyRoomtypeRoute)],
  exports: [RouterModule],
})
export class CyRoomtypeRoutingModule {}
