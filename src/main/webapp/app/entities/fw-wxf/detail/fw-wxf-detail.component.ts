import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFwWxf } from '../fw-wxf.model';

@Component({
  selector: 'jhi-fw-wxf-detail',
  templateUrl: './fw-wxf-detail.component.html',
})
export class FwWxfDetailComponent implements OnInit {
  fwWxf: IFwWxf | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fwWxf }) => {
      this.fwWxf = fwWxf;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
