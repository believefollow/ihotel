import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DCktypeComponent } from './list/d-cktype.component';
import { DCktypeDetailComponent } from './detail/d-cktype-detail.component';
import { DCktypeUpdateComponent } from './update/d-cktype-update.component';
import { DCktypeDeleteDialogComponent } from './delete/d-cktype-delete-dialog.component';
import { DCktypeRoutingModule } from './route/d-cktype-routing.module';

@NgModule({
  imports: [SharedModule, DCktypeRoutingModule],
  declarations: [DCktypeComponent, DCktypeDetailComponent, DCktypeUpdateComponent, DCktypeDeleteDialogComponent],
  entryComponents: [DCktypeDeleteDialogComponent],
})
export class DCktypeModule {}
