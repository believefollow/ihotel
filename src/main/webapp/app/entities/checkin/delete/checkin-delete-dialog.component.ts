import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICheckin } from '../checkin.model';
import { CheckinService } from '../service/checkin.service';

@Component({
  templateUrl: './checkin-delete-dialog.component.html',
})
export class CheckinDeleteDialogComponent {
  checkin?: ICheckin;

  constructor(protected checkinService: CheckinService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.checkinService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
