import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDUnit } from '../d-unit.model';

@Component({
  selector: 'jhi-d-unit-detail',
  templateUrl: './d-unit-detail.component.html',
})
export class DUnitDetailComponent implements OnInit {
  dUnit: IDUnit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dUnit }) => {
      this.dUnit = dUnit;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
