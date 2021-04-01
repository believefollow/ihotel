import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccPp } from '../acc-pp.model';
import { AccPpService } from '../service/acc-pp.service';

@Component({
  templateUrl: './acc-pp-delete-dialog.component.html',
})
export class AccPpDeleteDialogComponent {
  accPp?: IAccPp;

  constructor(protected accPpService: AccPpService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accPpService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
