import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDCktime } from '../d-cktime.model';

@Component({
  selector: 'jhi-d-cktime-detail',
  templateUrl: './d-cktime-detail.component.html',
})
export class DCktimeDetailComponent implements OnInit {
  dCktime: IDCktime | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dCktime }) => {
      this.dCktime = dCktime;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
