import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICzBqz } from '../cz-bqz.model';
import { CzBqzService } from '../service/cz-bqz.service';

@Component({
  templateUrl: './cz-bqz-delete-dialog.component.html',
})
export class CzBqzDeleteDialogComponent {
  czBqz?: ICzBqz;

  constructor(protected czBqzService: CzBqzService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.czBqzService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
