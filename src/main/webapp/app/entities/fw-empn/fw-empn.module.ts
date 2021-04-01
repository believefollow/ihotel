import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { FwEmpnComponent } from './list/fw-empn.component';
import { FwEmpnDetailComponent } from './detail/fw-empn-detail.component';
import { FwEmpnUpdateComponent } from './update/fw-empn-update.component';
import { FwEmpnDeleteDialogComponent } from './delete/fw-empn-delete-dialog.component';
import { FwEmpnRoutingModule } from './route/fw-empn-routing.module';

@NgModule({
  imports: [SharedModule, FwEmpnRoutingModule],
  declarations: [FwEmpnComponent, FwEmpnDetailComponent, FwEmpnUpdateComponent, FwEmpnDeleteDialogComponent],
  entryComponents: [FwEmpnDeleteDialogComponent],
})
export class FwEmpnModule {}
