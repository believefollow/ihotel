import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFwDs } from '../fw-ds.model';

@Component({
  selector: 'jhi-fw-ds-detail',
  templateUrl: './fw-ds-detail.component.html',
})
export class FwDsDetailComponent implements OnInit {
  fwDs: IFwDs | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fwDs }) => {
      this.fwDs = fwDs;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
