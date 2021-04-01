import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CyRoomtypeComponent } from './list/cy-roomtype.component';
import { CyRoomtypeDetailComponent } from './detail/cy-roomtype-detail.component';
import { CyRoomtypeUpdateComponent } from './update/cy-roomtype-update.component';
import { CyRoomtypeDeleteDialogComponent } from './delete/cy-roomtype-delete-dialog.component';
import { CyRoomtypeRoutingModule } from './route/cy-roomtype-routing.module';

@NgModule({
  imports: [SharedModule, CyRoomtypeRoutingModule],
  declarations: [CyRoomtypeComponent, CyRoomtypeDetailComponent, CyRoomtypeUpdateComponent, CyRoomtypeDeleteDialogComponent],
  entryComponents: [CyRoomtypeDeleteDialogComponent],
})
export class CyRoomtypeModule {}
