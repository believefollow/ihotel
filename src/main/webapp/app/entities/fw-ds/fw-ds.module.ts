import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { FwDsComponent } from './list/fw-ds.component';
import { FwDsDetailComponent } from './detail/fw-ds-detail.component';
import { FwDsUpdateComponent } from './update/fw-ds-update.component';
import { FwDsDeleteDialogComponent } from './delete/fw-ds-delete-dialog.component';
import { FwDsRoutingModule } from './route/fw-ds-routing.module';

@NgModule({
  imports: [SharedModule, FwDsRoutingModule],
  declarations: [FwDsComponent, FwDsDetailComponent, FwDsUpdateComponent, FwDsDeleteDialogComponent],
  entryComponents: [FwDsDeleteDialogComponent],
})
export class FwDsModule {}
