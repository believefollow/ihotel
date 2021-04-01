import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DXsComponent } from './list/d-xs.component';
import { DXsDetailComponent } from './detail/d-xs-detail.component';
import { DXsUpdateComponent } from './update/d-xs-update.component';
import { DXsDeleteDialogComponent } from './delete/d-xs-delete-dialog.component';
import { DXsRoutingModule } from './route/d-xs-routing.module';

@NgModule({
  imports: [SharedModule, DXsRoutingModule],
  declarations: [DXsComponent, DXsDetailComponent, DXsUpdateComponent, DXsDeleteDialogComponent],
  entryComponents: [DXsDeleteDialogComponent],
})
export class DXsModule {}
