import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICyCptype } from '../cy-cptype.model';

@Component({
  selector: 'jhi-cy-cptype-detail',
  templateUrl: './cy-cptype-detail.component.html',
})
export class CyCptypeDetailComponent implements OnInit {
  cyCptype: ICyCptype | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cyCptype }) => {
      this.cyCptype = cyCptype;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
