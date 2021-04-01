import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICzlCz } from '../czl-cz.model';
import { CzlCzService } from '../service/czl-cz.service';

@Component({
  templateUrl: './czl-cz-delete-dialog.component.html',
})
export class CzlCzDeleteDialogComponent {
  czlCz?: ICzlCz;

  constructor(protected czlCzService: CzlCzService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.czlCzService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
