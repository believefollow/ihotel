import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CzCzl3Component } from './list/cz-czl-3.component';
import { CzCzl3DetailComponent } from './detail/cz-czl-3-detail.component';
import { CzCzl3UpdateComponent } from './update/cz-czl-3-update.component';
import { CzCzl3DeleteDialogComponent } from './delete/cz-czl-3-delete-dialog.component';
import { CzCzl3RoutingModule } from './route/cz-czl-3-routing.module';

@NgModule({
  imports: [SharedModule, CzCzl3RoutingModule],
  declarations: [CzCzl3Component, CzCzl3DetailComponent, CzCzl3UpdateComponent, CzCzl3DeleteDialogComponent],
  entryComponents: [CzCzl3DeleteDialogComponent],
})
export class CzCzl3Module {}
