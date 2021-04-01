import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DCkComponent } from './list/d-ck.component';
import { DCkDetailComponent } from './detail/d-ck-detail.component';
import { DCkUpdateComponent } from './update/d-ck-update.component';
import { DCkDeleteDialogComponent } from './delete/d-ck-delete-dialog.component';
import { DCkRoutingModule } from './route/d-ck-routing.module';

@NgModule({
  imports: [SharedModule, DCkRoutingModule],
  declarations: [DCkComponent, DCkDetailComponent, DCkUpdateComponent, DCkDeleteDialogComponent],
  entryComponents: [DCkDeleteDialogComponent],
})
export class DCkModule {}
