import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICheckinBak } from '../checkin-bak.model';
import { CheckinBakService } from '../service/checkin-bak.service';

@Component({
  templateUrl: './checkin-bak-delete-dialog.component.html',
})
export class CheckinBakDeleteDialogComponent {
  checkinBak?: ICheckinBak;

  constructor(protected checkinBakService: CheckinBakService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.checkinBakService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
