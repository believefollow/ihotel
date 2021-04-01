import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AccountsComponent } from '../list/accounts.component';
import { AccountsDetailComponent } from '../detail/accounts-detail.component';
import { AccountsUpdateComponent } from '../update/accounts-update.component';
import { AccountsRoutingResolveService } from './accounts-routing-resolve.service';

const accountsRoute: Routes = [
  {
    path: '',
    component: AccountsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AccountsDetailComponent,
    resolve: {
      accounts: AccountsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccountsUpdateComponent,
    resolve: {
      accounts: AccountsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AccountsUpdateComponent,
    resolve: {
      accounts: AccountsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(accountsRoute)],
  exports: [RouterModule],
})
export class AccountsRoutingModule {}
