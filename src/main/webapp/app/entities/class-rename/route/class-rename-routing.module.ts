import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ClassRenameComponent } from '../list/class-rename.component';
import { ClassRenameDetailComponent } from '../detail/class-rename-detail.component';
import { ClassRenameUpdateComponent } from '../update/class-rename-update.component';
import { ClassRenameRoutingResolveService } from './class-rename-routing-resolve.service';

const classRenameRoute: Routes = [
  {
    path: '',
    component: ClassRenameComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClassRenameDetailComponent,
    resolve: {
      classRename: ClassRenameRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClassRenameUpdateComponent,
    resolve: {
      classRename: ClassRenameRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClassRenameUpdateComponent,
    resolve: {
      classRename: ClassRenameRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(classRenameRoute)],
  exports: [RouterModule],
})
export class ClassRenameRoutingModule {}
