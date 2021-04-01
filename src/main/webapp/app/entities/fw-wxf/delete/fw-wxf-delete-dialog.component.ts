import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFwWxf } from '../fw-wxf.model';
import { FwWxfService } from '../service/fw-wxf.service';

@Component({
  templateUrl: './fw-wxf-delete-dialog.component.html',
})
export class FwWxfDeleteDialogComponent {
  fwWxf?: IFwWxf;

  constructor(protected fwWxfService: FwWxfService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fwWxfService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
