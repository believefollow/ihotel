import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { AccPComponent } from './list/acc-p.component';
import { AccPDetailComponent } from './detail/acc-p-detail.component';
import { AccPUpdateComponent } from './update/acc-p-update.component';
import { AccPDeleteDialogComponent } from './delete/acc-p-delete-dialog.component';
import { AccPRoutingModule } from './route/acc-p-routing.module';

@NgModule({
  imports: [SharedModule, AccPRoutingModule],
  declarations: [AccPComponent, AccPDetailComponent, AccPUpdateComponent, AccPDeleteDialogComponent],
  entryComponents: [AccPDeleteDialogComponent],
})
export class AccPModule {}
