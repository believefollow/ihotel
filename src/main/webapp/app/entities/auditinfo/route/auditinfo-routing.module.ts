import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AuditinfoComponent } from '../list/auditinfo.component';
import { AuditinfoDetailComponent } from '../detail/auditinfo-detail.component';
import { AuditinfoUpdateComponent } from '../update/auditinfo-update.component';
import { AuditinfoRoutingResolveService } from './auditinfo-routing-resolve.service';

const auditinfoRoute: Routes = [
  {
    path: '',
    component: AuditinfoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AuditinfoDetailComponent,
    resolve: {
      auditinfo: AuditinfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AuditinfoUpdateComponent,
    resolve: {
      auditinfo: AuditinfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AuditinfoUpdateComponent,
    resolve: {
      auditinfo: AuditinfoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(auditinfoRoute)],
  exports: [RouterModule],
})
export class AuditinfoRoutingModule {}
