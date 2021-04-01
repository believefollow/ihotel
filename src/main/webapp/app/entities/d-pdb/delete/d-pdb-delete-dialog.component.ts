import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDPdb } from '../d-pdb.model';
import { DPdbService } from '../service/d-pdb.service';

@Component({
  templateUrl: './d-pdb-delete-dialog.component.html',
})
export class DPdbDeleteDialogComponent {
  dPdb?: IDPdb;

  constructor(protected dPdbService: DPdbService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dPdbService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
