import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ErrlogComponent } from './list/errlog.component';
import { ErrlogDetailComponent } from './detail/errlog-detail.component';
import { ErrlogUpdateComponent } from './update/errlog-update.component';
import { ErrlogDeleteDialogComponent } from './delete/errlog-delete-dialog.component';
import { ErrlogRoutingModule } from './route/errlog-routing.module';

@NgModule({
  imports: [SharedModule, ErrlogRoutingModule],
  declarations: [ErrlogComponent, ErrlogDetailComponent, ErrlogUpdateComponent, ErrlogDeleteDialogComponent],
  entryComponents: [ErrlogDeleteDialogComponent],
})
export class ErrlogModule {}
