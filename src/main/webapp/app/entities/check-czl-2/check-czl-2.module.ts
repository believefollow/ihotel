import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CheckCzl2Component } from './list/check-czl-2.component';
import { CheckCzl2DetailComponent } from './detail/check-czl-2-detail.component';
import { CheckCzl2UpdateComponent } from './update/check-czl-2-update.component';
import { CheckCzl2DeleteDialogComponent } from './delete/check-czl-2-delete-dialog.component';
import { CheckCzl2RoutingModule } from './route/check-czl-2-routing.module';

@NgModule({
  imports: [SharedModule, CheckCzl2RoutingModule],
  declarations: [CheckCzl2Component, CheckCzl2DetailComponent, CheckCzl2UpdateComponent, CheckCzl2DeleteDialogComponent],
  entryComponents: [CheckCzl2DeleteDialogComponent],
})
export class CheckCzl2Module {}
