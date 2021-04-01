import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DTypeComponent } from './list/d-type.component';
import { DTypeDetailComponent } from './detail/d-type-detail.component';
import { DTypeUpdateComponent } from './update/d-type-update.component';
import { DTypeDeleteDialogComponent } from './delete/d-type-delete-dialog.component';
import { DTypeRoutingModule } from './route/d-type-routing.module';

@NgModule({
  imports: [SharedModule, DTypeRoutingModule],
  declarations: [DTypeComponent, DTypeDetailComponent, DTypeUpdateComponent, DTypeDeleteDialogComponent],
  entryComponents: [DTypeDeleteDialogComponent],
})
export class DTypeModule {}
