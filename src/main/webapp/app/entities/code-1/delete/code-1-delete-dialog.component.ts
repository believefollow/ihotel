import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICode1 } from '../code-1.model';
import { Code1Service } from '../service/code-1.service';

@Component({
  templateUrl: './code-1-delete-dialog.component.html',
})
export class Code1DeleteDialogComponent {
  code1?: ICode1;

  constructor(protected code1Service: Code1Service, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.code1Service.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
