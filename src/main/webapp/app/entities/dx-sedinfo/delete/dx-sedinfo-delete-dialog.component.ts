import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDxSedinfo } from '../dx-sedinfo.model';
import { DxSedinfoService } from '../service/dx-sedinfo.service';

@Component({
  templateUrl: './dx-sedinfo-delete-dialog.component.html',
})
export class DxSedinfoDeleteDialogComponent {
  dxSedinfo?: IDxSedinfo;

  constructor(protected dxSedinfoService: DxSedinfoService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dxSedinfoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
