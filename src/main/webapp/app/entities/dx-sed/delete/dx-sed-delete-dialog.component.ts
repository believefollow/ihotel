import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDxSed } from '../dx-sed.model';
import { DxSedService } from '../service/dx-sed.service';

@Component({
  templateUrl: './dx-sed-delete-dialog.component.html',
})
export class DxSedDeleteDialogComponent {
  dxSed?: IDxSed;

  constructor(protected dxSedService: DxSedService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dxSedService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
