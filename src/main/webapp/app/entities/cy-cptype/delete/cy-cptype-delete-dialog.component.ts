import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICyCptype } from '../cy-cptype.model';
import { CyCptypeService } from '../service/cy-cptype.service';

@Component({
  templateUrl: './cy-cptype-delete-dialog.component.html',
})
export class CyCptypeDeleteDialogComponent {
  cyCptype?: ICyCptype;

  constructor(protected cyCptypeService: CyCptypeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cyCptypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
