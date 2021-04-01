import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AccbillnoComponent } from '../list/accbillno.component';
import { AccbillnoDetailComponent } from '../detail/accbillno-detail.component';
import { AccbillnoUpdateComponent } from '../update/accbillno-update.component';
import { AccbillnoRoutingResolveService } from './accbillno-routing-resolve.service';

const accbillnoRoute: Routes = [
  {
    path: '',
    component: AccbillnoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AccbillnoDetailComponent,
    resolve: {
      accbillno: AccbillnoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccbillnoUpdateComponent,
    resolve: {
      accbillno: AccbillnoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AccbillnoUpdateComponent,
    resolve: {
      accbillno: AccbillnoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(accbillnoRoute)],
  exports: [RouterModule],
})
export class AccbillnoRoutingModule {}
