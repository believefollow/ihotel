import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { BookingtimeComponent } from './list/bookingtime.component';
import { BookingtimeDetailComponent } from './detail/bookingtime-detail.component';
import { BookingtimeUpdateComponent } from './update/bookingtime-update.component';
import { BookingtimeDeleteDialogComponent } from './delete/bookingtime-delete-dialog.component';
import { BookingtimeRoutingModule } from './route/bookingtime-routing.module';

@NgModule({
  imports: [SharedModule, BookingtimeRoutingModule],
  declarations: [BookingtimeComponent, BookingtimeDetailComponent, BookingtimeUpdateComponent, BookingtimeDeleteDialogComponent],
  entryComponents: [BookingtimeDeleteDialogComponent],
})
export class BookingtimeModule {}
