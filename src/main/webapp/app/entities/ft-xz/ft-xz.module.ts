import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { FtXzComponent } from './list/ft-xz.component';
import { FtXzDetailComponent } from './detail/ft-xz-detail.component';
import { FtXzUpdateComponent } from './update/ft-xz-update.component';
import { FtXzDeleteDialogComponent } from './delete/ft-xz-delete-dialog.component';
import { FtXzRoutingModule } from './route/ft-xz-routing.module';

@NgModule({
  imports: [SharedModule, FtXzRoutingModule],
  declarations: [FtXzComponent, FtXzDetailComponent, FtXzUpdateComponent, FtXzDeleteDialogComponent],
  entryComponents: [FtXzDeleteDialogComponent],
})
export class FtXzModule {}
