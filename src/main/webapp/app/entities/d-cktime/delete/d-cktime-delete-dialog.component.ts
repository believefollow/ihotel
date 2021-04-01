import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDCktime } from '../d-cktime.model';
import { DCktimeService } from '../service/d-cktime.service';

@Component({
  templateUrl: './d-cktime-delete-dialog.component.html',
})
export class DCktimeDeleteDialogComponent {
  dCktime?: IDCktime;

  constructor(protected dCktimeService: DCktimeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dCktimeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
