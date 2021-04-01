import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DxSedComponent } from './list/dx-sed.component';
import { DxSedDetailComponent } from './detail/dx-sed-detail.component';
import { DxSedUpdateComponent } from './update/dx-sed-update.component';
import { DxSedDeleteDialogComponent } from './delete/dx-sed-delete-dialog.component';
import { DxSedRoutingModule } from './route/dx-sed-routing.module';

@NgModule({
  imports: [SharedModule, DxSedRoutingModule],
  declarations: [DxSedComponent, DxSedDetailComponent, DxSedUpdateComponent, DxSedDeleteDialogComponent],
  entryComponents: [DxSedDeleteDialogComponent],
})
export class DxSedModule {}
