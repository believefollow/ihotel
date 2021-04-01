import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDCk } from '../d-ck.model';

@Component({
  selector: 'jhi-d-ck-detail',
  templateUrl: './d-ck-detail.component.html',
})
export class DCkDetailComponent implements OnInit {
  dCk: IDCk | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dCk }) => {
      this.dCk = dCk;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
