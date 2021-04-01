import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckCzl2 } from '../check-czl-2.model';

@Component({
  selector: 'jhi-check-czl-2-detail',
  templateUrl: './check-czl-2-detail.component.html',
})
export class CheckCzl2DetailComponent implements OnInit {
  checkCzl2: ICheckCzl2 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkCzl2 }) => {
      this.checkCzl2 = checkCzl2;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
