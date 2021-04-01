import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DxSedinfoComponent } from './list/dx-sedinfo.component';
import { DxSedinfoDetailComponent } from './detail/dx-sedinfo-detail.component';
import { DxSedinfoUpdateComponent } from './update/dx-sedinfo-update.component';
import { DxSedinfoDeleteDialogComponent } from './delete/dx-sedinfo-delete-dialog.component';
import { DxSedinfoRoutingModule } from './route/dx-sedinfo-routing.module';

@NgModule({
  imports: [SharedModule, DxSedinfoRoutingModule],
  declarations: [DxSedinfoComponent, DxSedinfoDetailComponent, DxSedinfoUpdateComponent, DxSedinfoDeleteDialogComponent],
  entryComponents: [DxSedinfoDeleteDialogComponent],
})
export class DxSedinfoModule {}
