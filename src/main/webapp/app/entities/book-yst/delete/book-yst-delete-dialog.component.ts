import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBookYst } from '../book-yst.model';
import { BookYstService } from '../service/book-yst.service';

@Component({
  templateUrl: './book-yst-delete-dialog.component.html',
})
export class BookYstDeleteDialogComponent {
  bookYst?: IBookYst;

  constructor(protected bookYstService: BookYstService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bookYstService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
