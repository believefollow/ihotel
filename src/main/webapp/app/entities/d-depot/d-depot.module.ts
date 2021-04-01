import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DDepotComponent } from './list/d-depot.component';
import { DDepotDetailComponent } from './detail/d-depot-detail.component';
import { DDepotUpdateComponent } from './update/d-depot-update.component';
import { DDepotDeleteDialogComponent } from './delete/d-depot-delete-dialog.component';
import { DDepotRoutingModule } from './route/d-depot-routing.module';

@NgModule({
  imports: [SharedModule, DDepotRoutingModule],
  declarations: [DDepotComponent, DDepotDetailComponent, DDepotUpdateComponent, DDepotDeleteDialogComponent],
  entryComponents: [DDepotDeleteDialogComponent],
})
export class DDepotModule {}
