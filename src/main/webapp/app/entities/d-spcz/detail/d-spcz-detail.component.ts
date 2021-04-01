import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDSpcz } from '../d-spcz.model';

@Component({
  selector: 'jhi-d-spcz-detail',
  templateUrl: './d-spcz-detail.component.html',
})
export class DSpczDetailComponent implements OnInit {
  dSpcz: IDSpcz | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dSpcz }) => {
      this.dSpcz = dSpcz;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
