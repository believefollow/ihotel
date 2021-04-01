import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { FwJywpComponent } from './list/fw-jywp.component';
import { FwJywpDetailComponent } from './detail/fw-jywp-detail.component';
import { FwJywpUpdateComponent } from './update/fw-jywp-update.component';
import { FwJywpDeleteDialogComponent } from './delete/fw-jywp-delete-dialog.component';
import { FwJywpRoutingModule } from './route/fw-jywp-routing.module';

@NgModule({
  imports: [SharedModule, FwJywpRoutingModule],
  declarations: [FwJywpComponent, FwJywpDetailComponent, FwJywpUpdateComponent, FwJywpDeleteDialogComponent],
  entryComponents: [FwJywpDeleteDialogComponent],
})
export class FwJywpModule {}
