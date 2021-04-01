import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { AccbillnoComponent } from './list/accbillno.component';
import { AccbillnoDetailComponent } from './detail/accbillno-detail.component';
import { AccbillnoUpdateComponent } from './update/accbillno-update.component';
import { AccbillnoDeleteDialogComponent } from './delete/accbillno-delete-dialog.component';
import { AccbillnoRoutingModule } from './route/accbillno-routing.module';

@NgModule({
  imports: [SharedModule, AccbillnoRoutingModule],
  declarations: [AccbillnoComponent, AccbillnoDetailComponent, AccbillnoUpdateComponent, AccbillnoDeleteDialogComponent],
  entryComponents: [AccbillnoDeleteDialogComponent],
})
export class AccbillnoModule {}
