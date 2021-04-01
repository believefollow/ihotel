import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CtClassComponent } from '../list/ct-class.component';
import { CtClassDetailComponent } from '../detail/ct-class-detail.component';
import { CtClassUpdateComponent } from '../update/ct-class-update.component';
import { CtClassRoutingResolveService } from './ct-class-routing-resolve.service';

const ctClassRoute: Routes = [
  {
    path: '',
    component: CtClassComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CtClassDetailComponent,
    resolve: {
      ctClass: CtClassRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CtClassUpdateComponent,
    resolve: {
      ctClass: CtClassRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CtClassUpdateComponent,
    resolve: {
      ctClass: CtClassRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ctClassRoute)],
  exports: [RouterModule],
})
export class CtClassRoutingModule {}
