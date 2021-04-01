import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CyCptypeComponent } from './list/cy-cptype.component';
import { CyCptypeDetailComponent } from './detail/cy-cptype-detail.component';
import { CyCptypeUpdateComponent } from './update/cy-cptype-update.component';
import { CyCptypeDeleteDialogComponent } from './delete/cy-cptype-delete-dialog.component';
import { CyCptypeRoutingModule } from './route/cy-cptype-routing.module';

@NgModule({
  imports: [SharedModule, CyCptypeRoutingModule],
  declarations: [CyCptypeComponent, CyCptypeDetailComponent, CyCptypeUpdateComponent, CyCptypeDeleteDialogComponent],
  entryComponents: [CyCptypeDeleteDialogComponent],
})
export class CyCptypeModule {}
