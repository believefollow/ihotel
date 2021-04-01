import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ClassRenameComponent } from './list/class-rename.component';
import { ClassRenameDetailComponent } from './detail/class-rename-detail.component';
import { ClassRenameUpdateComponent } from './update/class-rename-update.component';
import { ClassRenameDeleteDialogComponent } from './delete/class-rename-delete-dialog.component';
import { ClassRenameRoutingModule } from './route/class-rename-routing.module';

@NgModule({
  imports: [SharedModule, ClassRenameRoutingModule],
  declarations: [ClassRenameComponent, ClassRenameDetailComponent, ClassRenameUpdateComponent, ClassRenameDeleteDialogComponent],
  entryComponents: [ClassRenameDeleteDialogComponent],
})
export class ClassRenameModule {}
