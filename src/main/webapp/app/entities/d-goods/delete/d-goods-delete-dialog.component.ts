import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDGoods } from '../d-goods.model';
import { DGoodsService } from '../service/d-goods.service';

@Component({
  templateUrl: './d-goods-delete-dialog.component.html',
})
export class DGoodsDeleteDialogComponent {
  dGoods?: IDGoods;

  constructor(protected dGoodsService: DGoodsService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dGoodsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
