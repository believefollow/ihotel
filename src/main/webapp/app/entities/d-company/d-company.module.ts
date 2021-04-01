import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DCompanyComponent } from './list/d-company.component';
import { DCompanyDetailComponent } from './detail/d-company-detail.component';
import { DCompanyUpdateComponent } from './update/d-company-update.component';
import { DCompanyDeleteDialogComponent } from './delete/d-company-delete-dialog.component';
import { DCompanyRoutingModule } from './route/d-company-routing.module';

@NgModule({
  imports: [SharedModule, DCompanyRoutingModule],
  declarations: [DCompanyComponent, DCompanyDetailComponent, DCompanyUpdateComponent, DCompanyDeleteDialogComponent],
  entryComponents: [DCompanyDeleteDialogComponent],
})
export class DCompanyModule {}
