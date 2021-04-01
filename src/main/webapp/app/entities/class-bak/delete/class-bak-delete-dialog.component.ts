import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IClassBak } from '../class-bak.model';
import { ClassBakService } from '../service/class-bak.service';

@Component({
  templateUrl: './class-bak-delete-dialog.component.html',
})
export class ClassBakDeleteDialogComponent {
  classBak?: IClassBak;

  constructor(protected classBakService: ClassBakService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.classBakService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
