import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ClassreportRoomComponent } from '../list/classreport-room.component';
import { ClassreportRoomDetailComponent } from '../detail/classreport-room-detail.component';
import { ClassreportRoomUpdateComponent } from '../update/classreport-room-update.component';
import { ClassreportRoomRoutingResolveService } from './classreport-room-routing-resolve.service';

const classreportRoomRoute: Routes = [
  {
    path: '',
    component: ClassreportRoomComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClassreportRoomDetailComponent,
    resolve: {
      classreportRoom: ClassreportRoomRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClassreportRoomUpdateComponent,
    resolve: {
      classreportRoom: ClassreportRoomRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClassreportRoomUpdateComponent,
    resolve: {
      classreportRoom: ClassreportRoomRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(classreportRoomRoute)],
  exports: [RouterModule],
})
export class ClassreportRoomRoutingModule {}
