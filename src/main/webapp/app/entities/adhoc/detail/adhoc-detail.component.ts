import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdhoc } from '../adhoc.model';

@Component({
  selector: 'jhi-adhoc-detail',
  templateUrl: './adhoc-detail.component.html',
})
export class AdhocDetailComponent implements OnInit {
  adhoc: IAdhoc | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adhoc }) => {
      this.adhoc = adhoc;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
