import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CheckinAccountComponent } from './list/checkin-account.component';
import { CheckinAccountDetailComponent } from './detail/checkin-account-detail.component';
import { CheckinAccountUpdateComponent } from './update/checkin-account-update.component';
import { CheckinAccountDeleteDialogComponent } from './delete/checkin-account-delete-dialog.component';
import { CheckinAccountRoutingModule } from './route/checkin-account-routing.module';

@NgModule({
  imports: [SharedModule, CheckinAccountRoutingModule],
  declarations: [
    CheckinAccountComponent,
    CheckinAccountDetailComponent,
    CheckinAccountUpdateComponent,
    CheckinAccountDeleteDialogComponent,
  ],
  entryComponents: [CheckinAccountDeleteDialogComponent],
})
export class CheckinAccountModule {}
