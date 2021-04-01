import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComset } from '../comset.model';

@Component({
  selector: 'jhi-comset-detail',
  templateUrl: './comset-detail.component.html',
})
export class ComsetDetailComponent implements OnInit {
  comset: IComset | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comset }) => {
      this.comset = comset;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
