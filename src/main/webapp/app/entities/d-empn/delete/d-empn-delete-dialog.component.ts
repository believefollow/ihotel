import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDEmpn } from '../d-empn.model';
import { DEmpnService } from '../service/d-empn.service';

@Component({
  templateUrl: './d-empn-delete-dialog.component.html',
})
export class DEmpnDeleteDialogComponent {
  dEmpn?: IDEmpn;

  constructor(protected dEmpnService: DEmpnService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dEmpnService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
