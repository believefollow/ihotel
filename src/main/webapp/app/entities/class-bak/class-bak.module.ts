import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ClassBakComponent } from './list/class-bak.component';
import { ClassBakDetailComponent } from './detail/class-bak-detail.component';
import { ClassBakUpdateComponent } from './update/class-bak-update.component';
import { ClassBakDeleteDialogComponent } from './delete/class-bak-delete-dialog.component';
import { ClassBakRoutingModule } from './route/class-bak-routing.module';

@NgModule({
  imports: [SharedModule, ClassBakRoutingModule],
  declarations: [ClassBakComponent, ClassBakDetailComponent, ClassBakUpdateComponent, ClassBakDeleteDialogComponent],
  entryComponents: [ClassBakDeleteDialogComponent],
})
export class ClassBakModule {}
