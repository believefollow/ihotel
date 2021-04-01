import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { AccountsComponent } from './list/accounts.component';
import { AccountsDetailComponent } from './detail/accounts-detail.component';
import { AccountsUpdateComponent } from './update/accounts-update.component';
import { AccountsDeleteDialogComponent } from './delete/accounts-delete-dialog.component';
import { AccountsRoutingModule } from './route/accounts-routing.module';

@NgModule({
  imports: [SharedModule, AccountsRoutingModule],
  declarations: [AccountsComponent, AccountsDetailComponent, AccountsUpdateComponent, AccountsDeleteDialogComponent],
  entryComponents: [AccountsDeleteDialogComponent],
})
export class AccountsModule {}
