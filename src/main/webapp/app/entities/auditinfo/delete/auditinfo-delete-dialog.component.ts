import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAuditinfo } from '../auditinfo.model';
import { AuditinfoService } from '../service/auditinfo.service';

@Component({
  templateUrl: './auditinfo-delete-dialog.component.html',
})
export class AuditinfoDeleteDialogComponent {
  auditinfo?: IAuditinfo;

  constructor(protected auditinfoService: AuditinfoService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.auditinfoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
