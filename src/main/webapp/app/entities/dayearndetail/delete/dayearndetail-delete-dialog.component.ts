import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDayearndetail } from '../dayearndetail.model';
import { DayearndetailService } from '../service/dayearndetail.service';

@Component({
  templateUrl: './dayearndetail-delete-dialog.component.html',
})
export class DayearndetailDeleteDialogComponent {
  dayearndetail?: IDayearndetail;

  constructor(protected dayearndetailService: DayearndetailService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dayearndetailService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
