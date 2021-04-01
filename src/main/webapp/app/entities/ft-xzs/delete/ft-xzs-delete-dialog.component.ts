import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFtXzs } from '../ft-xzs.model';
import { FtXzsService } from '../service/ft-xzs.service';

@Component({
  templateUrl: './ft-xzs-delete-dialog.component.html',
})
export class FtXzsDeleteDialogComponent {
  ftXzs?: IFtXzs;

  constructor(protected ftXzsService: FtXzsService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ftXzsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
