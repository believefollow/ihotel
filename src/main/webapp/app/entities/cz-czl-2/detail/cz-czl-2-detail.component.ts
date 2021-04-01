import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICzCzl2 } from '../cz-czl-2.model';

@Component({
  selector: 'jhi-cz-czl-2-detail',
  templateUrl: './cz-czl-2-detail.component.html',
})
export class CzCzl2DetailComponent implements OnInit {
  czCzl2: ICzCzl2 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ czCzl2 }) => {
      this.czCzl2 = czCzl2;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
