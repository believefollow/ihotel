import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DKcComponent } from './list/d-kc.component';
import { DKcDetailComponent } from './detail/d-kc-detail.component';
import { DKcUpdateComponent } from './update/d-kc-update.component';
import { DKcDeleteDialogComponent } from './delete/d-kc-delete-dialog.component';
import { DKcRoutingModule } from './route/d-kc-routing.module';

@NgModule({
  imports: [SharedModule, DKcRoutingModule],
  declarations: [DKcComponent, DKcDetailComponent, DKcUpdateComponent, DKcDeleteDialogComponent],
  entryComponents: [DKcDeleteDialogComponent],
})
export class DKcModule {}
