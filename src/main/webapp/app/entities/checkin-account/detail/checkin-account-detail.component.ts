import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckinAccount } from '../checkin-account.model';

@Component({
  selector: 'jhi-checkin-account-detail',
  templateUrl: './checkin-account-detail.component.html',
})
export class CheckinAccountDetailComponent implements OnInit {
  checkinAccount: ICheckinAccount | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkinAccount }) => {
      this.checkinAccount = checkinAccount;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
