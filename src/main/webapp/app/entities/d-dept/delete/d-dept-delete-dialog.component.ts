import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDDept } from '../d-dept.model';
import { DDeptService } from '../service/d-dept.service';

@Component({
  templateUrl: './d-dept-delete-dialog.component.html',
})
export class DDeptDeleteDialogComponent {
  dDept?: IDDept;

  constructor(protected dDeptService: DDeptService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dDeptService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
