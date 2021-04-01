import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICardysq } from '../cardysq.model';
import { CardysqService } from '../service/cardysq.service';

@Component({
  templateUrl: './cardysq-delete-dialog.component.html',
})
export class CardysqDeleteDialogComponent {
  cardysq?: ICardysq;

  constructor(protected cardysqService: CardysqService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cardysqService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
