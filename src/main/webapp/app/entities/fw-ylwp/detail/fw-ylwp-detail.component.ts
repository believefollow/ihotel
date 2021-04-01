import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFwYlwp } from '../fw-ylwp.model';

@Component({
  selector: 'jhi-fw-ylwp-detail',
  templateUrl: './fw-ylwp-detail.component.html',
})
export class FwYlwpDetailComponent implements OnInit {
  fwYlwp: IFwYlwp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fwYlwp }) => {
      this.fwYlwp = fwYlwp;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
