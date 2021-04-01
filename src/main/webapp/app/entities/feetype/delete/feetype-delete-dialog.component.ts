import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFeetype } from '../feetype.model';
import { FeetypeService } from '../service/feetype.service';

@Component({
  templateUrl: './feetype-delete-dialog.component.html',
})
export class FeetypeDeleteDialogComponent {
  feetype?: IFeetype;

  constructor(protected feetypeService: FeetypeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.feetypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
