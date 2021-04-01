import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckCzl } from '../check-czl.model';

@Component({
  selector: 'jhi-check-czl-detail',
  templateUrl: './check-czl-detail.component.html',
})
export class CheckCzlDetailComponent implements OnInit {
  checkCzl: ICheckCzl | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkCzl }) => {
      this.checkCzl = checkCzl;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
