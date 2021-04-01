import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDCktype } from '../d-cktype.model';
import { DCktypeService } from '../service/d-cktype.service';

@Component({
  templateUrl: './d-cktype-delete-dialog.component.html',
})
export class DCktypeDeleteDialogComponent {
  dCktype?: IDCktype;

  constructor(protected dCktypeService: DCktypeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dCktypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
