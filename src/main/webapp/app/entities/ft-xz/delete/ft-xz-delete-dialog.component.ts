import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFtXz } from '../ft-xz.model';
import { FtXzService } from '../service/ft-xz.service';

@Component({
  templateUrl: './ft-xz-delete-dialog.component.html',
})
export class FtXzDeleteDialogComponent {
  ftXz?: IFtXz;

  constructor(protected ftXzService: FtXzService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ftXzService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
