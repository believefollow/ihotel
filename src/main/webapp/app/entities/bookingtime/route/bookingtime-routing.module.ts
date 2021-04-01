import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BookingtimeComponent } from '../list/bookingtime.component';
import { BookingtimeDetailComponent } from '../detail/bookingtime-detail.component';
import { BookingtimeUpdateComponent } from '../update/bookingtime-update.component';
import { BookingtimeRoutingResolveService } from './bookingtime-routing-resolve.service';

const bookingtimeRoute: Routes = [
  {
    path: '',
    component: BookingtimeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BookingtimeDetailComponent,
    resolve: {
      bookingtime: BookingtimeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BookingtimeUpdateComponent,
    resolve: {
      bookingtime: BookingtimeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BookingtimeUpdateComponent,
    resolve: {
      bookingtime: BookingtimeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bookingtimeRoute)],
  exports: [RouterModule],
})
export class BookingtimeRoutingModule {}
