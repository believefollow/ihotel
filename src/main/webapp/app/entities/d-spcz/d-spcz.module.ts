import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DSpczComponent } from './list/d-spcz.component';
import { DSpczDetailComponent } from './detail/d-spcz-detail.component';
import { DSpczUpdateComponent } from './update/d-spcz-update.component';
import { DSpczDeleteDialogComponent } from './delete/d-spcz-delete-dialog.component';
import { DSpczRoutingModule } from './route/d-spcz-routing.module';

@NgModule({
  imports: [SharedModule, DSpczRoutingModule],
  declarations: [DSpczComponent, DSpczDetailComponent, DSpczUpdateComponent, DSpczDeleteDialogComponent],
  entryComponents: [DSpczDeleteDialogComponent],
})
export class DSpczModule {}
