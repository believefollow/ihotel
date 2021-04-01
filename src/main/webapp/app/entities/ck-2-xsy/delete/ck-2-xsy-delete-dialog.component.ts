import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICk2xsy } from '../ck-2-xsy.model';
import { Ck2xsyService } from '../service/ck-2-xsy.service';

@Component({
  templateUrl: './ck-2-xsy-delete-dialog.component.html',
})
export class Ck2xsyDeleteDialogComponent {
  ck2xsy?: ICk2xsy;

  constructor(protected ck2xsyService: Ck2xsyService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ck2xsyService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
