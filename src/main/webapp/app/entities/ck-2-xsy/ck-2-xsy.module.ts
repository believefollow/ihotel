import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { Ck2xsyComponent } from './list/ck-2-xsy.component';
import { Ck2xsyDetailComponent } from './detail/ck-2-xsy-detail.component';
import { Ck2xsyUpdateComponent } from './update/ck-2-xsy-update.component';
import { Ck2xsyDeleteDialogComponent } from './delete/ck-2-xsy-delete-dialog.component';
import { Ck2xsyRoutingModule } from './route/ck-2-xsy-routing.module';

@NgModule({
  imports: [SharedModule, Ck2xsyRoutingModule],
  declarations: [Ck2xsyComponent, Ck2xsyDetailComponent, Ck2xsyUpdateComponent, Ck2xsyDeleteDialogComponent],
  entryComponents: [Ck2xsyDeleteDialogComponent],
})
export class Ck2xsyModule {}
