import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDKc } from '../d-kc.model';

@Component({
  selector: 'jhi-d-kc-detail',
  templateUrl: './d-kc-detail.component.html',
})
export class DKcDetailComponent implements OnInit {
  dKc: IDKc | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dKc }) => {
      this.dKc = dKc;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
