import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BookYstComponent } from '../list/book-yst.component';
import { BookYstDetailComponent } from '../detail/book-yst-detail.component';
import { BookYstUpdateComponent } from '../update/book-yst-update.component';
import { BookYstRoutingResolveService } from './book-yst-routing-resolve.service';

const bookYstRoute: Routes = [
  {
    path: '',
    component: BookYstComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BookYstDetailComponent,
    resolve: {
      bookYst: BookYstRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BookYstUpdateComponent,
    resolve: {
      bookYst: BookYstRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BookYstUpdateComponent,
    resolve: {
      bookYst: BookYstRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bookYstRoute)],
  exports: [RouterModule],
})
export class BookYstRoutingModule {}
