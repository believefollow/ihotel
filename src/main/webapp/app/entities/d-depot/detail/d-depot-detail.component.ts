import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDDepot } from '../d-depot.model';

@Component({
  selector: 'jhi-d-depot-detail',
  templateUrl: './d-depot-detail.component.html',
})
export class DDepotDetailComponent implements OnInit {
  dDepot: IDDepot | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dDepot }) => {
      this.dDepot = dDepot;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
