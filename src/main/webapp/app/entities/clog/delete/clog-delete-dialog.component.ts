import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IClog } from '../clog.model';
import { ClogService } from '../service/clog.service';

@Component({
  templateUrl: './clog-delete-dialog.component.html',
})
export class ClogDeleteDialogComponent {
  clog?: IClog;

  constructor(protected clogService: ClogService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.clogService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
