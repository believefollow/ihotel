import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICheckinAccount } from '../checkin-account.model';
import { CheckinAccountService } from '../service/checkin-account.service';

@Component({
  templateUrl: './checkin-account-delete-dialog.component.html',
})
export class CheckinAccountDeleteDialogComponent {
  checkinAccount?: ICheckinAccount;

  constructor(protected checkinAccountService: CheckinAccountService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.checkinAccountService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
