import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { FwWxfComponent } from './list/fw-wxf.component';
import { FwWxfDetailComponent } from './detail/fw-wxf-detail.component';
import { FwWxfUpdateComponent } from './update/fw-wxf-update.component';
import { FwWxfDeleteDialogComponent } from './delete/fw-wxf-delete-dialog.component';
import { FwWxfRoutingModule } from './route/fw-wxf-routing.module';

@NgModule({
  imports: [SharedModule, FwWxfRoutingModule],
  declarations: [FwWxfComponent, FwWxfDetailComponent, FwWxfUpdateComponent, FwWxfDeleteDialogComponent],
  entryComponents: [FwWxfDeleteDialogComponent],
})
export class FwWxfModule {}
