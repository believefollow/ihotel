import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DRkComponent } from './list/d-rk.component';
import { DRkDetailComponent } from './detail/d-rk-detail.component';
import { DRkUpdateComponent } from './update/d-rk-update.component';
import { DRkDeleteDialogComponent } from './delete/d-rk-delete-dialog.component';
import { DRkRoutingModule } from './route/d-rk-routing.module';

@NgModule({
  imports: [SharedModule, DRkRoutingModule],
  declarations: [DRkComponent, DRkDetailComponent, DRkUpdateComponent, DRkDeleteDialogComponent],
  entryComponents: [DRkDeleteDialogComponent],
})
export class DRkModule {}
