import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFwYlwp } from '../fw-ylwp.model';
import { FwYlwpService } from '../service/fw-ylwp.service';

@Component({
  templateUrl: './fw-ylwp-delete-dialog.component.html',
})
export class FwYlwpDeleteDialogComponent {
  fwYlwp?: IFwYlwp;

  constructor(protected fwYlwpService: FwYlwpService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fwYlwpService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
