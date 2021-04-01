import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDKc } from '../d-kc.model';
import { DKcService } from '../service/d-kc.service';

@Component({
  templateUrl: './d-kc-delete-dialog.component.html',
})
export class DKcDeleteDialogComponent {
  dKc?: IDKc;

  constructor(protected dKcService: DKcService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dKcService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
