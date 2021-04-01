import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckin } from '../checkin.model';

@Component({
  selector: 'jhi-checkin-detail',
  templateUrl: './checkin-detail.component.html',
})
export class CheckinDetailComponent implements OnInit {
  checkin: ICheckin | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkin }) => {
      this.checkin = checkin;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
