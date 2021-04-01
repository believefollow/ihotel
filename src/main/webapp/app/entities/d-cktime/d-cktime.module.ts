import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DCktimeComponent } from './list/d-cktime.component';
import { DCktimeDetailComponent } from './detail/d-cktime-detail.component';
import { DCktimeUpdateComponent } from './update/d-cktime-update.component';
import { DCktimeDeleteDialogComponent } from './delete/d-cktime-delete-dialog.component';
import { DCktimeRoutingModule } from './route/d-cktime-routing.module';

@NgModule({
  imports: [SharedModule, DCktimeRoutingModule],
  declarations: [DCktimeComponent, DCktimeDetailComponent, DCktimeUpdateComponent, DCktimeDeleteDialogComponent],
  entryComponents: [DCktimeDeleteDialogComponent],
})
export class DCktimeModule {}
