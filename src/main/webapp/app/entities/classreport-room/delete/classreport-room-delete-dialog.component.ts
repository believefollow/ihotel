import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IClassreportRoom } from '../classreport-room.model';
import { ClassreportRoomService } from '../service/classreport-room.service';

@Component({
  templateUrl: './classreport-room-delete-dialog.component.html',
})
export class ClassreportRoomDeleteDialogComponent {
  classreportRoom?: IClassreportRoom;

  constructor(protected classreportRoomService: ClassreportRoomService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.classreportRoomService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
