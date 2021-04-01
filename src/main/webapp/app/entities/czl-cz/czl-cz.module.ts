import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CzlCzComponent } from './list/czl-cz.component';
import { CzlCzDetailComponent } from './detail/czl-cz-detail.component';
import { CzlCzUpdateComponent } from './update/czl-cz-update.component';
import { CzlCzDeleteDialogComponent } from './delete/czl-cz-delete-dialog.component';
import { CzlCzRoutingModule } from './route/czl-cz-routing.module';

@NgModule({
  imports: [SharedModule, CzlCzRoutingModule],
  declarations: [CzlCzComponent, CzlCzDetailComponent, CzlCzUpdateComponent, CzlCzDeleteDialogComponent],
  entryComponents: [CzlCzDeleteDialogComponent],
})
export class CzlCzModule {}
