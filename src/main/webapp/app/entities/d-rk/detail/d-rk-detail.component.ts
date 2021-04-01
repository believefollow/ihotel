import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDRk } from '../d-rk.model';

@Component({
  selector: 'jhi-d-rk-detail',
  templateUrl: './d-rk-detail.component.html',
})
export class DRkDetailComponent implements OnInit {
  dRk: IDRk | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dRk }) => {
      this.dRk = dRk;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
