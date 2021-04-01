import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CardysqComponent } from './list/cardysq.component';
import { CardysqDetailComponent } from './detail/cardysq-detail.component';
import { CardysqUpdateComponent } from './update/cardysq-update.component';
import { CardysqDeleteDialogComponent } from './delete/cardysq-delete-dialog.component';
import { CardysqRoutingModule } from './route/cardysq-routing.module';

@NgModule({
  imports: [SharedModule, CardysqRoutingModule],
  declarations: [CardysqComponent, CardysqDetailComponent, CardysqUpdateComponent, CardysqDeleteDialogComponent],
  entryComponents: [CardysqDeleteDialogComponent],
})
export class CardysqModule {}
