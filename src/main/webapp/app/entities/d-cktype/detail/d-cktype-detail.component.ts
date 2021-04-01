import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDCktype } from '../d-cktype.model';

@Component({
  selector: 'jhi-d-cktype-detail',
  templateUrl: './d-cktype-detail.component.html',
})
export class DCktypeDetailComponent implements OnInit {
  dCktype: IDCktype | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dCktype }) => {
      this.dCktype = dCktype;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
