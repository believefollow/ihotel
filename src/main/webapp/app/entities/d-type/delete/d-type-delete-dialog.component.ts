import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDType } from '../d-type.model';
import { DTypeService } from '../service/d-type.service';

@Component({
  templateUrl: './d-type-delete-dialog.component.html',
})
export class DTypeDeleteDialogComponent {
  dType?: IDType;

  constructor(protected dTypeService: DTypeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
