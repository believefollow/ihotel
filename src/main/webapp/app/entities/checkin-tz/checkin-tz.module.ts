import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CheckinTzComponent } from './list/checkin-tz.component';
import { CheckinTzDetailComponent } from './detail/checkin-tz-detail.component';
import { CheckinTzUpdateComponent } from './update/checkin-tz-update.component';
import { CheckinTzDeleteDialogComponent } from './delete/checkin-tz-delete-dialog.component';
import { CheckinTzRoutingModule } from './route/checkin-tz-routing.module';

@NgModule({
  imports: [SharedModule, CheckinTzRoutingModule],
  declarations: [CheckinTzComponent, CheckinTzDetailComponent, CheckinTzUpdateComponent, CheckinTzDeleteDialogComponent],
  entryComponents: [CheckinTzDeleteDialogComponent],
})
export class CheckinTzModule {}
