import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAcc } from '../acc.model';
import { AccService } from '../service/acc.service';

@Component({
  templateUrl: './acc-delete-dialog.component.html',
})
export class AccDeleteDialogComponent {
  acc?: IAcc;

  constructor(protected accService: AccService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
