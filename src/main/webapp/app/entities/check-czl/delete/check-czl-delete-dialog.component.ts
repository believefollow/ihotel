import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICheckCzl } from '../check-czl.model';
import { CheckCzlService } from '../service/check-czl.service';

@Component({
  templateUrl: './check-czl-delete-dialog.component.html',
})
export class CheckCzlDeleteDialogComponent {
  checkCzl?: ICheckCzl;

  constructor(protected checkCzlService: CheckCzlService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.checkCzlService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
