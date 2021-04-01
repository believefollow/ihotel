import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CtClassComponent } from './list/ct-class.component';
import { CtClassDetailComponent } from './detail/ct-class-detail.component';
import { CtClassUpdateComponent } from './update/ct-class-update.component';
import { CtClassDeleteDialogComponent } from './delete/ct-class-delete-dialog.component';
import { CtClassRoutingModule } from './route/ct-class-routing.module';

@NgModule({
  imports: [SharedModule, CtClassRoutingModule],
  declarations: [CtClassComponent, CtClassDetailComponent, CtClassUpdateComponent, CtClassDeleteDialogComponent],
  entryComponents: [CtClassDeleteDialogComponent],
})
export class CtClassModule {}
