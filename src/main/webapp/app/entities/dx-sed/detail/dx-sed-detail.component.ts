import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDxSed } from '../dx-sed.model';

@Component({
  selector: 'jhi-dx-sed-detail',
  templateUrl: './dx-sed-detail.component.html',
})
export class DxSedDetailComponent implements OnInit {
  dxSed: IDxSed | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dxSed }) => {
      this.dxSed = dxSed;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
