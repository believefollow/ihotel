import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { FeetypeComponent } from './list/feetype.component';
import { FeetypeDetailComponent } from './detail/feetype-detail.component';
import { FeetypeUpdateComponent } from './update/feetype-update.component';
import { FeetypeDeleteDialogComponent } from './delete/feetype-delete-dialog.component';
import { FeetypeRoutingModule } from './route/feetype-routing.module';

@NgModule({
  imports: [SharedModule, FeetypeRoutingModule],
  declarations: [FeetypeComponent, FeetypeDetailComponent, FeetypeUpdateComponent, FeetypeDeleteDialogComponent],
  entryComponents: [FeetypeDeleteDialogComponent],
})
export class FeetypeModule {}
