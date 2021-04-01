import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { Code1Component } from '../list/code-1.component';
import { Code1DetailComponent } from '../detail/code-1-detail.component';
import { Code1UpdateComponent } from '../update/code-1-update.component';
import { Code1RoutingResolveService } from './code-1-routing-resolve.service';

const code1Route: Routes = [
  {
    path: '',
    component: Code1Component,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: Code1DetailComponent,
    resolve: {
      code1: Code1RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: Code1UpdateComponent,
    resolve: {
      code1: Code1RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: Code1UpdateComponent,
    resolve: {
      code1: Code1RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(code1Route)],
  exports: [RouterModule],
})
export class Code1RoutingModule {}
