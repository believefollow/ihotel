import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { AdhocComponent } from './list/adhoc.component';
import { AdhocDetailComponent } from './detail/adhoc-detail.component';
import { AdhocUpdateComponent } from './update/adhoc-update.component';
import { AdhocDeleteDialogComponent } from './delete/adhoc-delete-dialog.component';
import { AdhocRoutingModule } from './route/adhoc-routing.module';

@NgModule({
  imports: [SharedModule, AdhocRoutingModule],
  declarations: [AdhocComponent, AdhocDetailComponent, AdhocUpdateComponent, AdhocDeleteDialogComponent],
  entryComponents: [AdhocDeleteDialogComponent],
})
export class AdhocModule {}
