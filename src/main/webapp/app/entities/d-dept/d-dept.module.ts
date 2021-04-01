import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DDeptComponent } from './list/d-dept.component';
import { DDeptDetailComponent } from './detail/d-dept-detail.component';
import { DDeptUpdateComponent } from './update/d-dept-update.component';
import { DDeptDeleteDialogComponent } from './delete/d-dept-delete-dialog.component';
import { DDeptRoutingModule } from './route/d-dept-routing.module';

@NgModule({
  imports: [SharedModule, DDeptRoutingModule],
  declarations: [DDeptComponent, DDeptDetailComponent, DDeptUpdateComponent, DDeptDeleteDialogComponent],
  entryComponents: [DDeptDeleteDialogComponent],
})
export class DDeptModule {}
