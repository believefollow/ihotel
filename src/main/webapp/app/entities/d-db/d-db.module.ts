import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DDbComponent } from './list/d-db.component';
import { DDbDetailComponent } from './detail/d-db-detail.component';
import { DDbUpdateComponent } from './update/d-db-update.component';
import { DDbDeleteDialogComponent } from './delete/d-db-delete-dialog.component';
import { DDbRoutingModule } from './route/d-db-routing.module';

@NgModule({
  imports: [SharedModule, DDbRoutingModule],
  declarations: [DDbComponent, DDbDetailComponent, DDbUpdateComponent, DDbDeleteDialogComponent],
  entryComponents: [DDbDeleteDialogComponent],
})
export class DDbModule {}
