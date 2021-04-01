import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDXs } from '../d-xs.model';

@Component({
  selector: 'jhi-d-xs-detail',
  templateUrl: './d-xs-detail.component.html',
})
export class DXsDetailComponent implements OnInit {
  dXs: IDXs | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dXs }) => {
      this.dXs = dXs;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
