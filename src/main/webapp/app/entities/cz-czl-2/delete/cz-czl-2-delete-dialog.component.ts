import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICzCzl2 } from '../cz-czl-2.model';
import { CzCzl2Service } from '../service/cz-czl-2.service';

@Component({
  templateUrl: './cz-czl-2-delete-dialog.component.html',
})
export class CzCzl2DeleteDialogComponent {
  czCzl2?: ICzCzl2;

  constructor(protected czCzl2Service: CzCzl2Service, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.czCzl2Service.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
