import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ClogComponent } from './list/clog.component';
import { ClogDetailComponent } from './detail/clog-detail.component';
import { ClogUpdateComponent } from './update/clog-update.component';
import { ClogDeleteDialogComponent } from './delete/clog-delete-dialog.component';
import { ClogRoutingModule } from './route/clog-routing.module';

@NgModule({
  imports: [SharedModule, ClogRoutingModule],
  declarations: [ClogComponent, ClogDetailComponent, ClogUpdateComponent, ClogDeleteDialogComponent],
  entryComponents: [ClogDeleteDialogComponent],
})
export class ClogModule {}
