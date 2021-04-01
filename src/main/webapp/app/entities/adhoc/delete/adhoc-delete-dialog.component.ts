import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdhoc } from '../adhoc.model';
import { AdhocService } from '../service/adhoc.service';

@Component({
  templateUrl: './adhoc-delete-dialog.component.html',
})
export class AdhocDeleteDialogComponent {
  adhoc?: IAdhoc;

  constructor(protected adhocService: AdhocService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.adhocService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
