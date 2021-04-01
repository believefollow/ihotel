import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDDb } from '../d-db.model';
import { DDbService } from '../service/d-db.service';

@Component({
  templateUrl: './d-db-delete-dialog.component.html',
})
export class DDbDeleteDialogComponent {
  dDb?: IDDb;

  constructor(protected dDbService: DDbService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dDbService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
