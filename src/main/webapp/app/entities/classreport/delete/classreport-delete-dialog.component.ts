import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IClassreport } from '../classreport.model';
import { ClassreportService } from '../service/classreport.service';

@Component({
  templateUrl: './classreport-delete-dialog.component.html',
})
export class ClassreportDeleteDialogComponent {
  classreport?: IClassreport;

  constructor(protected classreportService: ClassreportService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.classreportService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
