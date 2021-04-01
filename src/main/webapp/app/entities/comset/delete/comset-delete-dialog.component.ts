import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IComset } from '../comset.model';
import { ComsetService } from '../service/comset.service';

@Component({
  templateUrl: './comset-delete-dialog.component.html',
})
export class ComsetDeleteDialogComponent {
  comset?: IComset;

  constructor(protected comsetService: ComsetService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.comsetService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
