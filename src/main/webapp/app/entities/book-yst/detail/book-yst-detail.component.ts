import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBookYst } from '../book-yst.model';

@Component({
  selector: 'jhi-book-yst-detail',
  templateUrl: './book-yst-detail.component.html',
})
export class BookYstDetailComponent implements OnInit {
  bookYst: IBookYst | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookYst }) => {
      this.bookYst = bookYst;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
