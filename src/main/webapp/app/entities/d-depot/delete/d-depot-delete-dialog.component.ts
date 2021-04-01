import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDDepot } from '../d-depot.model';
import { DDepotService } from '../service/d-depot.service';

@Component({
  templateUrl: './d-depot-delete-dialog.component.html',
})
export class DDepotDeleteDialogComponent {
  dDepot?: IDDepot;

  constructor(protected dDepotService: DDepotService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dDepotService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
