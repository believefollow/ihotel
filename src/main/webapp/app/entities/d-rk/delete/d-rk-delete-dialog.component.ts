import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDRk } from '../d-rk.model';
import { DRkService } from '../service/d-rk.service';

@Component({
  templateUrl: './d-rk-delete-dialog.component.html',
})
export class DRkDeleteDialogComponent {
  dRk?: IDRk;

  constructor(protected dRkService: DRkService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dRkService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
