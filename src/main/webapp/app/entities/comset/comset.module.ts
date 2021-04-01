import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ComsetComponent } from './list/comset.component';
import { ComsetDetailComponent } from './detail/comset-detail.component';
import { ComsetUpdateComponent } from './update/comset-update.component';
import { ComsetDeleteDialogComponent } from './delete/comset-delete-dialog.component';
import { ComsetRoutingModule } from './route/comset-routing.module';

@NgModule({
  imports: [SharedModule, ComsetRoutingModule],
  declarations: [ComsetComponent, ComsetDetailComponent, ComsetUpdateComponent, ComsetDeleteDialogComponent],
  entryComponents: [ComsetDeleteDialogComponent],
})
export class ComsetModule {}
