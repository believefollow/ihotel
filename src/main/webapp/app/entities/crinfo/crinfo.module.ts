import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CrinfoComponent } from './list/crinfo.component';
import { CrinfoDetailComponent } from './detail/crinfo-detail.component';
import { CrinfoUpdateComponent } from './update/crinfo-update.component';
import { CrinfoDeleteDialogComponent } from './delete/crinfo-delete-dialog.component';
import { CrinfoRoutingModule } from './route/crinfo-routing.module';

@NgModule({
  imports: [SharedModule, CrinfoRoutingModule],
  declarations: [CrinfoComponent, CrinfoDetailComponent, CrinfoUpdateComponent, CrinfoDeleteDialogComponent],
  entryComponents: [CrinfoDeleteDialogComponent],
})
export class CrinfoModule {}
