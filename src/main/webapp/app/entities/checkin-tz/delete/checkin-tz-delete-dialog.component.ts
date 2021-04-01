import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICheckinTz } from '../checkin-tz.model';
import { CheckinTzService } from '../service/checkin-tz.service';

@Component({
  templateUrl: './checkin-tz-delete-dialog.component.html',
})
export class CheckinTzDeleteDialogComponent {
  checkinTz?: ICheckinTz;

  constructor(protected checkinTzService: CheckinTzService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.checkinTzService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
