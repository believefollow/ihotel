import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { AccPpComponent } from './list/acc-pp.component';
import { AccPpDetailComponent } from './detail/acc-pp-detail.component';
import { AccPpUpdateComponent } from './update/acc-pp-update.component';
import { AccPpDeleteDialogComponent } from './delete/acc-pp-delete-dialog.component';
import { AccPpRoutingModule } from './route/acc-pp-routing.module';

@NgModule({
  imports: [SharedModule, AccPpRoutingModule],
  declarations: [AccPpComponent, AccPpDetailComponent, AccPpUpdateComponent, AccPpDeleteDialogComponent],
  entryComponents: [AccPpDeleteDialogComponent],
})
export class AccPpModule {}
