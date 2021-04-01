import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDSpcz } from '../d-spcz.model';
import { DSpczService } from '../service/d-spcz.service';

@Component({
  templateUrl: './d-spcz-delete-dialog.component.html',
})
export class DSpczDeleteDialogComponent {
  dSpcz?: IDSpcz;

  constructor(protected dSpczService: DSpczService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dSpczService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
