import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccP } from '../acc-p.model';

@Component({
  selector: 'jhi-acc-p-detail',
  templateUrl: './acc-p-detail.component.html',
})
export class AccPDetailComponent implements OnInit {
  accP: IAccP | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accP }) => {
      this.accP = accP;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
