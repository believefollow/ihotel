import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ClassreportRoomComponent } from './list/classreport-room.component';
import { ClassreportRoomDetailComponent } from './detail/classreport-room-detail.component';
import { ClassreportRoomUpdateComponent } from './update/classreport-room-update.component';
import { ClassreportRoomDeleteDialogComponent } from './delete/classreport-room-delete-dialog.component';
import { ClassreportRoomRoutingModule } from './route/classreport-room-routing.module';

@NgModule({
  imports: [SharedModule, ClassreportRoomRoutingModule],
  declarations: [
    ClassreportRoomComponent,
    ClassreportRoomDetailComponent,
    ClassreportRoomUpdateComponent,
    ClassreportRoomDeleteDialogComponent,
  ],
  entryComponents: [ClassreportRoomDeleteDialogComponent],
})
export class ClassreportRoomModule {}
