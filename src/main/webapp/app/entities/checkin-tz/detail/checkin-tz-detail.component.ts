import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckinTz } from '../checkin-tz.model';

@Component({
  selector: 'jhi-checkin-tz-detail',
  templateUrl: './checkin-tz-detail.component.html',
})
export class CheckinTzDetailComponent implements OnInit {
  checkinTz: ICheckinTz | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkinTz }) => {
      this.checkinTz = checkinTz;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
