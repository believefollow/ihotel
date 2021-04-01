import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBookingtime } from '../bookingtime.model';

@Component({
  selector: 'jhi-bookingtime-detail',
  templateUrl: './bookingtime-detail.component.html',
})
export class BookingtimeDetailComponent implements OnInit {
  bookingtime: IBookingtime | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookingtime }) => {
      this.bookingtime = bookingtime;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
