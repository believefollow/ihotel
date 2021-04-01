import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ClassreportComponent } from '../list/classreport.component';
import { ClassreportDetailComponent } from '../detail/classreport-detail.component';
import { ClassreportUpdateComponent } from '../update/classreport-update.component';
import { ClassreportRoutingResolveService } from './classreport-routing-resolve.service';

const classreportRoute: Routes = [
  {
    path: '',
    component: ClassreportComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClassreportDetailComponent,
    resolve: {
      classreport: ClassreportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClassreportUpdateComponent,
    resolve: {
      classreport: ClassreportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClassreportUpdateComponent,
    resolve: {
      classreport: ClassreportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(classreportRoute)],
  exports: [RouterModule],
})
export class ClassreportRoutingModule {}
