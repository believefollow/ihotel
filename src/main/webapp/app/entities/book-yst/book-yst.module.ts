import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { BookYstComponent } from './list/book-yst.component';
import { BookYstDetailComponent } from './detail/book-yst-detail.component';
import { BookYstUpdateComponent } from './update/book-yst-update.component';
import { BookYstDeleteDialogComponent } from './delete/book-yst-delete-dialog.component';
import { BookYstRoutingModule } from './route/book-yst-routing.module';

@NgModule({
  imports: [SharedModule, BookYstRoutingModule],
  declarations: [BookYstComponent, BookYstDetailComponent, BookYstUpdateComponent, BookYstDeleteDialogComponent],
  entryComponents: [BookYstDeleteDialogComponent],
})
export class BookYstModule {}
