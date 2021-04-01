import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICtClass } from '../ct-class.model';
import { CtClassService } from '../service/ct-class.service';

@Component({
  templateUrl: './ct-class-delete-dialog.component.html',
})
export class CtClassDeleteDialogComponent {
  ctClass?: ICtClass;

  constructor(protected ctClassService: CtClassService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ctClassService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
