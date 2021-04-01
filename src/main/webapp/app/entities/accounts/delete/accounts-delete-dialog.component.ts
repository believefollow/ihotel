import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccounts } from '../accounts.model';
import { AccountsService } from '../service/accounts.service';

@Component({
  templateUrl: './accounts-delete-dialog.component.html',
})
export class AccountsDeleteDialogComponent {
  accounts?: IAccounts;

  constructor(protected accountsService: AccountsService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accountsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
