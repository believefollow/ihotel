import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFkCz } from '../fk-cz.model';
import { FkCzService } from '../service/fk-cz.service';

@Component({
  templateUrl: './fk-cz-delete-dialog.component.html',
})
export class FkCzDeleteDialogComponent {
  fkCz?: IFkCz;

  constructor(protected fkCzService: FkCzService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fkCzService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
