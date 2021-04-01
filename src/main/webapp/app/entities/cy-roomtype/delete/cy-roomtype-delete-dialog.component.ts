import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICyRoomtype } from '../cy-roomtype.model';
import { CyRoomtypeService } from '../service/cy-roomtype.service';

@Component({
  templateUrl: './cy-roomtype-delete-dialog.component.html',
})
export class CyRoomtypeDeleteDialogComponent {
  cyRoomtype?: ICyRoomtype;

  constructor(protected cyRoomtypeService: CyRoomtypeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cyRoomtypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
