import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CzCzl2Component } from './list/cz-czl-2.component';
import { CzCzl2DetailComponent } from './detail/cz-czl-2-detail.component';
import { CzCzl2UpdateComponent } from './update/cz-czl-2-update.component';
import { CzCzl2DeleteDialogComponent } from './delete/cz-czl-2-delete-dialog.component';
import { CzCzl2RoutingModule } from './route/cz-czl-2-routing.module';

@NgModule({
  imports: [SharedModule, CzCzl2RoutingModule],
  declarations: [CzCzl2Component, CzCzl2DetailComponent, CzCzl2UpdateComponent, CzCzl2DeleteDialogComponent],
  entryComponents: [CzCzl2DeleteDialogComponent],
})
export class CzCzl2Module {}
