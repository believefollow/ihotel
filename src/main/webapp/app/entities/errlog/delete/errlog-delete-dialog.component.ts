import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IErrlog } from '../errlog.model';
import { ErrlogService } from '../service/errlog.service';

@Component({
  templateUrl: './errlog-delete-dialog.component.html',
})
export class ErrlogDeleteDialogComponent {
  errlog?: IErrlog;

  constructor(protected errlogService: ErrlogService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.errlogService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
