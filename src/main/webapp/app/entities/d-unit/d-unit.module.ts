import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DUnitComponent } from './list/d-unit.component';
import { DUnitDetailComponent } from './detail/d-unit-detail.component';
import { DUnitUpdateComponent } from './update/d-unit-update.component';
import { DUnitDeleteDialogComponent } from './delete/d-unit-delete-dialog.component';
import { DUnitRoutingModule } from './route/d-unit-routing.module';

@NgModule({
  imports: [SharedModule, DUnitRoutingModule],
  declarations: [DUnitComponent, DUnitDetailComponent, DUnitUpdateComponent, DUnitDeleteDialogComponent],
  entryComponents: [DUnitDeleteDialogComponent],
})
export class DUnitModule {}
