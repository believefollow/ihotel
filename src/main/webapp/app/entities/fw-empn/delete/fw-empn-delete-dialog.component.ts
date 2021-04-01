import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFwEmpn } from '../fw-empn.model';
import { FwEmpnService } from '../service/fw-empn.service';

@Component({
  templateUrl: './fw-empn-delete-dialog.component.html',
})
export class FwEmpnDeleteDialogComponent {
  fwEmpn?: IFwEmpn;

  constructor(protected fwEmpnService: FwEmpnService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fwEmpnService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
