import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { FtXzsComponent } from './list/ft-xzs.component';
import { FtXzsDetailComponent } from './detail/ft-xzs-detail.component';
import { FtXzsUpdateComponent } from './update/ft-xzs-update.component';
import { FtXzsDeleteDialogComponent } from './delete/ft-xzs-delete-dialog.component';
import { FtXzsRoutingModule } from './route/ft-xzs-routing.module';

@NgModule({
  imports: [SharedModule, FtXzsRoutingModule],
  declarations: [FtXzsComponent, FtXzsDetailComponent, FtXzsUpdateComponent, FtXzsDeleteDialogComponent],
  entryComponents: [FtXzsDeleteDialogComponent],
})
export class FtXzsModule {}
