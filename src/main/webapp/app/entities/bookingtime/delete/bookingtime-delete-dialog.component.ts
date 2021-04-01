import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBookingtime } from '../bookingtime.model';
import { BookingtimeService } from '../service/bookingtime.service';

@Component({
  templateUrl: './bookingtime-delete-dialog.component.html',
})
export class BookingtimeDeleteDialogComponent {
  bookingtime?: IBookingtime;

  constructor(protected bookingtimeService: BookingtimeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bookingtimeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
