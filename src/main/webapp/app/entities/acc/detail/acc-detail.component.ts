import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAcc } from '../acc.model';

@Component({
  selector: 'jhi-acc-detail',
  templateUrl: './acc-detail.component.html',
})
export class AccDetailComponent implements OnInit {
  acc: IAcc | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ acc }) => {
      this.acc = acc;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
