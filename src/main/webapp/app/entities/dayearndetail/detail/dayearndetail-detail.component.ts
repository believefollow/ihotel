import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDayearndetail } from '../dayearndetail.model';

@Component({
  selector: 'jhi-dayearndetail-detail',
  templateUrl: './dayearndetail-detail.component.html',
})
export class DayearndetailDetailComponent implements OnInit {
  dayearndetail: IDayearndetail | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dayearndetail }) => {
      this.dayearndetail = dayearndetail;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
