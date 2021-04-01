import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFwJywp } from '../fw-jywp.model';

@Component({
  selector: 'jhi-fw-jywp-detail',
  templateUrl: './fw-jywp-detail.component.html',
})
export class FwJywpDetailComponent implements OnInit {
  fwJywp: IFwJywp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fwJywp }) => {
      this.fwJywp = fwJywp;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
