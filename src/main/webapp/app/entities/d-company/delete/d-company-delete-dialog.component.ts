import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDCompany } from '../d-company.model';
import { DCompanyService } from '../service/d-company.service';

@Component({
  templateUrl: './d-company-delete-dialog.component.html',
})
export class DCompanyDeleteDialogComponent {
  dCompany?: IDCompany;

  constructor(protected dCompanyService: DCompanyService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dCompanyService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
