import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICzlCz } from '../czl-cz.model';

@Component({
  selector: 'jhi-czl-cz-detail',
  templateUrl: './czl-cz-detail.component.html',
})
export class CzlCzDetailComponent implements OnInit {
  czlCz: ICzlCz | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ czlCz }) => {
      this.czlCz = czlCz;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
