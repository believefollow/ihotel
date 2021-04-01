import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DEmpnComponent } from './list/d-empn.component';
import { DEmpnDetailComponent } from './detail/d-empn-detail.component';
import { DEmpnUpdateComponent } from './update/d-empn-update.component';
import { DEmpnDeleteDialogComponent } from './delete/d-empn-delete-dialog.component';
import { DEmpnRoutingModule } from './route/d-empn-routing.module';

@NgModule({
  imports: [SharedModule, DEmpnRoutingModule],
  declarations: [DEmpnComponent, DEmpnDetailComponent, DEmpnUpdateComponent, DEmpnDeleteDialogComponent],
  entryComponents: [DEmpnDeleteDialogComponent],
})
export class DEmpnModule {}
