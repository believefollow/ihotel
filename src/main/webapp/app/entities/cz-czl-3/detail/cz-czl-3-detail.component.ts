import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICzCzl3 } from '../cz-czl-3.model';

@Component({
  selector: 'jhi-cz-czl-3-detail',
  templateUrl: './cz-czl-3-detail.component.html',
})
export class CzCzl3DetailComponent implements OnInit {
  czCzl3: ICzCzl3 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ czCzl3 }) => {
      this.czCzl3 = czCzl3;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
