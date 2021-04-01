import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICardysq } from '../cardysq.model';

@Component({
  selector: 'jhi-cardysq-detail',
  templateUrl: './cardysq-detail.component.html',
})
export class CardysqDetailComponent implements OnInit {
  cardysq: ICardysq | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cardysq }) => {
      this.cardysq = cardysq;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
