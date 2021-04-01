import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IClassRename } from '../class-rename.model';
import { ClassRenameService } from '../service/class-rename.service';

@Component({
  templateUrl: './class-rename-delete-dialog.component.html',
})
export class ClassRenameDeleteDialogComponent {
  classRename?: IClassRename;

  constructor(protected classRenameService: ClassRenameService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.classRenameService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
