import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFwEmpn } from '../fw-empn.model';

@Component({
  selector: 'jhi-fw-empn-detail',
  templateUrl: './fw-empn-detail.component.html',
})
export class FwEmpnDetailComponent implements OnInit {
  fwEmpn: IFwEmpn | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fwEmpn }) => {
      this.fwEmpn = fwEmpn;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
