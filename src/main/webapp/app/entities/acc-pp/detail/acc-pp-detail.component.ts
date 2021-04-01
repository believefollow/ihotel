import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccPp } from '../acc-pp.model';

@Component({
  selector: 'jhi-acc-pp-detail',
  templateUrl: './acc-pp-detail.component.html',
})
export class AccPpDetailComponent implements OnInit {
  accPp: IAccPp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accPp }) => {
      this.accPp = accPp;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
