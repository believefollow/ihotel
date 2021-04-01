import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { Code1Component } from './list/code-1.component';
import { Code1DetailComponent } from './detail/code-1-detail.component';
import { Code1UpdateComponent } from './update/code-1-update.component';
import { Code1DeleteDialogComponent } from './delete/code-1-delete-dialog.component';
import { Code1RoutingModule } from './route/code-1-routing.module';

@NgModule({
  imports: [SharedModule, Code1RoutingModule],
  declarations: [Code1Component, Code1DetailComponent, Code1UpdateComponent, Code1DeleteDialogComponent],
  entryComponents: [Code1DeleteDialogComponent],
})
export class Code1Module {}
