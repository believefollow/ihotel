import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { FkCzComponent } from './list/fk-cz.component';
import { FkCzDetailComponent } from './detail/fk-cz-detail.component';
import { FkCzUpdateComponent } from './update/fk-cz-update.component';
import { FkCzDeleteDialogComponent } from './delete/fk-cz-delete-dialog.component';
import { FkCzRoutingModule } from './route/fk-cz-routing.module';

@NgModule({
  imports: [SharedModule, FkCzRoutingModule],
  declarations: [FkCzComponent, FkCzDetailComponent, FkCzUpdateComponent, FkCzDeleteDialogComponent],
  entryComponents: [FkCzDeleteDialogComponent],
})
export class FkCzModule {}
