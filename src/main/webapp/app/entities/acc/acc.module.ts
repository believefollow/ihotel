import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { AccComponent } from './list/acc.component';
import { AccDetailComponent } from './detail/acc-detail.component';
import { AccUpdateComponent } from './update/acc-update.component';
import { AccDeleteDialogComponent } from './delete/acc-delete-dialog.component';
import { AccRoutingModule } from './route/acc-routing.module';

@NgModule({
  imports: [SharedModule, AccRoutingModule],
  declarations: [AccComponent, AccDetailComponent, AccUpdateComponent, AccDeleteDialogComponent],
  entryComponents: [AccDeleteDialogComponent],
})
export class AccModule {}
