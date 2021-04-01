import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ClassreportComponent } from './list/classreport.component';
import { ClassreportDetailComponent } from './detail/classreport-detail.component';
import { ClassreportUpdateComponent } from './update/classreport-update.component';
import { ClassreportDeleteDialogComponent } from './delete/classreport-delete-dialog.component';
import { ClassreportRoutingModule } from './route/classreport-routing.module';

@NgModule({
  imports: [SharedModule, ClassreportRoutingModule],
  declarations: [ClassreportComponent, ClassreportDetailComponent, ClassreportUpdateComponent, ClassreportDeleteDialogComponent],
  entryComponents: [ClassreportDeleteDialogComponent],
})
export class ClassreportModule {}
