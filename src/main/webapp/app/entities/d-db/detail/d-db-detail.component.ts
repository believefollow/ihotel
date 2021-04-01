import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDDb } from '../d-db.model';

@Component({
  selector: 'jhi-d-db-detail',
  templateUrl: './d-db-detail.component.html',
})
export class DDbDetailComponent implements OnInit {
  dDb: IDDb | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dDb }) => {
      this.dDb = dDb;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
