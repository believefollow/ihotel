import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICheckCzl2 } from '../check-czl-2.model';
import { CheckCzl2Service } from '../service/check-czl-2.service';

@Component({
  templateUrl: './check-czl-2-delete-dialog.component.html',
})
export class CheckCzl2DeleteDialogComponent {
  checkCzl2?: ICheckCzl2;

  constructor(protected checkCzl2Service: CheckCzl2Service, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.checkCzl2Service.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
