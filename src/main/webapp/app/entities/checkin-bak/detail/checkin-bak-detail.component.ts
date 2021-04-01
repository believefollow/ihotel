import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckinBak } from '../checkin-bak.model';

@Component({
  selector: 'jhi-checkin-bak-detail',
  templateUrl: './checkin-bak-detail.component.html',
})
export class CheckinBakDetailComponent implements OnInit {
  checkinBak: ICheckinBak | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkinBak }) => {
      this.checkinBak = checkinBak;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
