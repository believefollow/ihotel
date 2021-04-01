import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DPdbComponent } from './list/d-pdb.component';
import { DPdbDetailComponent } from './detail/d-pdb-detail.component';
import { DPdbUpdateComponent } from './update/d-pdb-update.component';
import { DPdbDeleteDialogComponent } from './delete/d-pdb-delete-dialog.component';
import { DPdbRoutingModule } from './route/d-pdb-routing.module';

@NgModule({
  imports: [SharedModule, DPdbRoutingModule],
  declarations: [DPdbComponent, DPdbDetailComponent, DPdbUpdateComponent, DPdbDeleteDialogComponent],
  entryComponents: [DPdbDeleteDialogComponent],
})
export class DPdbModule {}
