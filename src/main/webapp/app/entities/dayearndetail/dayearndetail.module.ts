import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DayearndetailComponent } from './list/dayearndetail.component';
import { DayearndetailDetailComponent } from './detail/dayearndetail-detail.component';
import { DayearndetailUpdateComponent } from './update/dayearndetail-update.component';
import { DayearndetailDeleteDialogComponent } from './delete/dayearndetail-delete-dialog.component';
import { DayearndetailRoutingModule } from './route/dayearndetail-routing.module';

@NgModule({
  imports: [SharedModule, DayearndetailRoutingModule],
  declarations: [DayearndetailComponent, DayearndetailDetailComponent, DayearndetailUpdateComponent, DayearndetailDeleteDialogComponent],
  entryComponents: [DayearndetailDeleteDialogComponent],
})
export class DayearndetailModule {}
