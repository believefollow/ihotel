import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDCk } from '../d-ck.model';
import { DCkService } from '../service/d-ck.service';

@Component({
  templateUrl: './d-ck-delete-dialog.component.html',
})
export class DCkDeleteDialogComponent {
  dCk?: IDCk;

  constructor(protected dCkService: DCkService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dCkService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
