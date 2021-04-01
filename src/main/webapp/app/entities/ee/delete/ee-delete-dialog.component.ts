import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEe } from '../ee.model';
import { EeService } from '../service/ee.service';

@Component({
  templateUrl: './ee-delete-dialog.component.html',
})
export class EeDeleteDialogComponent {
  ee?: IEe;

  constructor(protected eeService: EeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
