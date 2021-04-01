import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ClassBakComponent } from '../list/class-bak.component';
import { ClassBakDetailComponent } from '../detail/class-bak-detail.component';
import { ClassBakUpdateComponent } from '../update/class-bak-update.component';
import { ClassBakRoutingResolveService } from './class-bak-routing-resolve.service';

const classBakRoute: Routes = [
  {
    path: '',
    component: ClassBakComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClassBakDetailComponent,
    resolve: {
      classBak: ClassBakRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClassBakUpdateComponent,
    resolve: {
      classBak: ClassBakRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClassBakUpdateComponent,
    resolve: {
      classBak: ClassBakRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(classBakRoute)],
  exports: [RouterModule],
})
export class ClassBakRoutingModule {}
