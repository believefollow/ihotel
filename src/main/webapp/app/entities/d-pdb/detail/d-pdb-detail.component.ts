import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDPdb } from '../d-pdb.model';

@Component({
  selector: 'jhi-d-pdb-detail',
  templateUrl: './d-pdb-detail.component.html',
})
export class DPdbDetailComponent implements OnInit {
  dPdb: IDPdb | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dPdb }) => {
      this.dPdb = dPdb;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
