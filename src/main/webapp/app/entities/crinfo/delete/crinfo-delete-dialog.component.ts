import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICrinfo } from '../crinfo.model';
import { CrinfoService } from '../service/crinfo.service';

@Component({
  templateUrl: './crinfo-delete-dialog.component.html',
})
export class CrinfoDeleteDialogComponent {
  crinfo?: ICrinfo;

  constructor(protected crinfoService: CrinfoService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.crinfoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
