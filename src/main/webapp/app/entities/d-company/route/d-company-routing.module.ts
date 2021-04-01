import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DCompanyComponent } from '../list/d-company.component';
import { DCompanyDetailComponent } from '../detail/d-company-detail.component';
import { DCompanyUpdateComponent } from '../update/d-company-update.component';
import { DCompanyRoutingResolveService } from './d-company-routing-resolve.service';

const dCompanyRoute: Routes = [
  {
    path: '',
    component: DCompanyComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DCompanyDetailComponent,
    resolve: {
      dCompany: DCompanyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DCompanyUpdateComponent,
    resolve: {
      dCompany: DCompanyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DCompanyUpdateComponent,
    resolve: {
      dCompany: DCompanyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dCompanyRoute)],
  exports: [RouterModule],
})
export class DCompanyRoutingModule {}
