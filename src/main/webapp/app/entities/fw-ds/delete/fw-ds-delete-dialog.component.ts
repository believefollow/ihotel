import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFwDs } from '../fw-ds.model';
import { FwDsService } from '../service/fw-ds.service';

@Component({
  templateUrl: './fw-ds-delete-dialog.component.html',
})
export class FwDsDeleteDialogComponent {
  fwDs?: IFwDs;

  constructor(protected fwDsService: FwDsService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fwDsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
