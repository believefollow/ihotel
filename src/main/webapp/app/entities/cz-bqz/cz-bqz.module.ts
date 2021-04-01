import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CzBqzComponent } from './list/cz-bqz.component';
import { CzBqzDetailComponent } from './detail/cz-bqz-detail.component';
import { CzBqzUpdateComponent } from './update/cz-bqz-update.component';
import { CzBqzDeleteDialogComponent } from './delete/cz-bqz-delete-dialog.component';
import { CzBqzRoutingModule } from './route/cz-bqz-routing.module';

@NgModule({
  imports: [SharedModule, CzBqzRoutingModule],
  declarations: [CzBqzComponent, CzBqzDetailComponent, CzBqzUpdateComponent, CzBqzDeleteDialogComponent],
  entryComponents: [CzBqzDeleteDialogComponent],
})
export class CzBqzModule {}
