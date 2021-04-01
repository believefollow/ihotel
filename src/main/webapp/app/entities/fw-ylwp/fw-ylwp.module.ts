import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { FwYlwpComponent } from './list/fw-ylwp.component';
import { FwYlwpDetailComponent } from './detail/fw-ylwp-detail.component';
import { FwYlwpUpdateComponent } from './update/fw-ylwp-update.component';
import { FwYlwpDeleteDialogComponent } from './delete/fw-ylwp-delete-dialog.component';
import { FwYlwpRoutingModule } from './route/fw-ylwp-routing.module';

@NgModule({
  imports: [SharedModule, FwYlwpRoutingModule],
  declarations: [FwYlwpComponent, FwYlwpDetailComponent, FwYlwpUpdateComponent, FwYlwpDeleteDialogComponent],
  entryComponents: [FwYlwpDeleteDialogComponent],
})
export class FwYlwpModule {}
