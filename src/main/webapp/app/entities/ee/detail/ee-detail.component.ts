import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEe } from '../ee.model';

@Component({
  selector: 'jhi-ee-detail',
  templateUrl: './ee-detail.component.html',
})
export class EeDetailComponent implements OnInit {
  ee: IEe | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ee }) => {
      this.ee = ee;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
