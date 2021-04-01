import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDType } from '../d-type.model';

@Component({
  selector: 'jhi-d-type-detail',
  templateUrl: './d-type-detail.component.html',
})
export class DTypeDetailComponent implements OnInit {
  dType: IDType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dType }) => {
      this.dType = dType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
