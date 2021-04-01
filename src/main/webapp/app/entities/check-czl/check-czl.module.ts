import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CheckCzlComponent } from './list/check-czl.component';
import { CheckCzlDetailComponent } from './detail/check-czl-detail.component';
import { CheckCzlUpdateComponent } from './update/check-czl-update.component';
import { CheckCzlDeleteDialogComponent } from './delete/check-czl-delete-dialog.component';
import { CheckCzlRoutingModule } from './route/check-czl-routing.module';

@NgModule({
  imports: [SharedModule, CheckCzlRoutingModule],
  declarations: [CheckCzlComponent, CheckCzlDetailComponent, CheckCzlUpdateComponent, CheckCzlDeleteDialogComponent],
  entryComponents: [CheckCzlDeleteDialogComponent],
})
export class CheckCzlModule {}
