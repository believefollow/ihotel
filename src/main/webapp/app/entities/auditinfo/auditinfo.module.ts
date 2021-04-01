import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { AuditinfoComponent } from './list/auditinfo.component';
import { AuditinfoDetailComponent } from './detail/auditinfo-detail.component';
import { AuditinfoUpdateComponent } from './update/auditinfo-update.component';
import { AuditinfoDeleteDialogComponent } from './delete/auditinfo-delete-dialog.component';
import { AuditinfoRoutingModule } from './route/auditinfo-routing.module';

@NgModule({
  imports: [SharedModule, AuditinfoRoutingModule],
  declarations: [AuditinfoComponent, AuditinfoDetailComponent, AuditinfoUpdateComponent, AuditinfoDeleteDialogComponent],
  entryComponents: [AuditinfoDeleteDialogComponent],
})
export class AuditinfoModule {}
