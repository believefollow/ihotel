import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccP } from '../acc-p.model';
import { AccPService } from '../service/acc-p.service';

@Component({
  templateUrl: './acc-p-delete-dialog.component.html',
})
export class AccPDeleteDialogComponent {
  accP?: IAccP;

  constructor(protected accPService: AccPService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accPService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
