import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccbillno } from '../accbillno.model';
import { AccbillnoService } from '../service/accbillno.service';

@Component({
  templateUrl: './accbillno-delete-dialog.component.html',
})
export class AccbillnoDeleteDialogComponent {
  accbillno?: IAccbillno;

  constructor(protected accbillnoService: AccbillnoService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accbillnoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
