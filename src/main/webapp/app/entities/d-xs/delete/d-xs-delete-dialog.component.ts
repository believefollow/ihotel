import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDXs } from '../d-xs.model';
import { DXsService } from '../service/d-xs.service';

@Component({
  templateUrl: './d-xs-delete-dialog.component.html',
})
export class DXsDeleteDialogComponent {
  dXs?: IDXs;

  constructor(protected dXsService: DXsService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dXsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
