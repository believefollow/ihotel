import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDGoods } from '../d-goods.model';

@Component({
  selector: 'jhi-d-goods-detail',
  templateUrl: './d-goods-detail.component.html',
})
export class DGoodsDetailComponent implements OnInit {
  dGoods: IDGoods | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dGoods }) => {
      this.dGoods = dGoods;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
