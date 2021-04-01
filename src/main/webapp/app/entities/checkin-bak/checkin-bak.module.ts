import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CheckinBakComponent } from './list/checkin-bak.component';
import { CheckinBakDetailComponent } from './detail/checkin-bak-detail.component';
import { CheckinBakUpdateComponent } from './update/checkin-bak-update.component';
import { CheckinBakDeleteDialogComponent } from './delete/checkin-bak-delete-dialog.component';
import { CheckinBakRoutingModule } from './route/checkin-bak-routing.module';

@NgModule({
  imports: [SharedModule, CheckinBakRoutingModule],
  declarations: [CheckinBakComponent, CheckinBakDetailComponent, CheckinBakUpdateComponent, CheckinBakDeleteDialogComponent],
  entryComponents: [CheckinBakDeleteDialogComponent],
})
export class CheckinBakModule {}
