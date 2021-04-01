import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CheckinComponent } from './list/checkin.component';
import { CheckinDetailComponent } from './detail/checkin-detail.component';
import { CheckinUpdateComponent } from './update/checkin-update.component';
import { CheckinDeleteDialogComponent } from './delete/checkin-delete-dialog.component';
import { CheckinRoutingModule } from './route/checkin-routing.module';

@NgModule({
  imports: [SharedModule, CheckinRoutingModule],
  declarations: [CheckinComponent, CheckinDetailComponent, CheckinUpdateComponent, CheckinDeleteDialogComponent],
  entryComponents: [CheckinDeleteDialogComponent],
})
export class CheckinModule {}
