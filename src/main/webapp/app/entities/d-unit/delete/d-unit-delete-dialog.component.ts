import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDUnit } from '../d-unit.model';
import { DUnitService } from '../service/d-unit.service';

@Component({
  templateUrl: './d-unit-delete-dialog.component.html',
})
export class DUnitDeleteDialogComponent {
  dUnit?: IDUnit;

  constructor(protected dUnitService: DUnitService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dUnitService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
