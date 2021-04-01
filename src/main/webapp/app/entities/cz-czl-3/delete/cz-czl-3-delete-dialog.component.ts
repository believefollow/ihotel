import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICzCzl3 } from '../cz-czl-3.model';
import { CzCzl3Service } from '../service/cz-czl-3.service';

@Component({
  templateUrl: './cz-czl-3-delete-dialog.component.html',
})
export class CzCzl3DeleteDialogComponent {
  czCzl3?: ICzCzl3;

  constructor(protected czCzl3Service: CzCzl3Service, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.czCzl3Service.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
