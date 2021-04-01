import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { EeComponent } from './list/ee.component';
import { EeDetailComponent } from './detail/ee-detail.component';
import { EeUpdateComponent } from './update/ee-update.component';
import { EeDeleteDialogComponent } from './delete/ee-delete-dialog.component';
import { EeRoutingModule } from './route/ee-routing.module';

@NgModule({
  imports: [SharedModule, EeRoutingModule],
  declarations: [EeComponent, EeDetailComponent, EeUpdateComponent, EeDeleteDialogComponent],
  entryComponents: [EeDeleteDialogComponent],
})
export class EeModule {}
