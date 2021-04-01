import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CardysqComponent } from '../list/cardysq.component';
import { CardysqDetailComponent } from '../detail/cardysq-detail.component';
import { CardysqUpdateComponent } from '../update/cardysq-update.component';
import { CardysqRoutingResolveService } from './cardysq-routing-resolve.service';

const cardysqRoute: Routes = [
  {
    path: '',
    component: CardysqComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CardysqDetailComponent,
    resolve: {
      cardysq: CardysqRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CardysqUpdateComponent,
    resolve: {
      cardysq: CardysqRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CardysqUpdateComponent,
    resolve: {
      cardysq: CardysqRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cardysqRoute)],
  exports: [RouterModule],
})
export class CardysqRoutingModule {}
