import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFwJywp } from '../fw-jywp.model';
import { FwJywpService } from '../service/fw-jywp.service';

@Component({
  templateUrl: './fw-jywp-delete-dialog.component.html',
})
export class FwJywpDeleteDialogComponent {
  fwJywp?: IFwJywp;

  constructor(protected fwJywpService: FwJywpService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fwJywpService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
