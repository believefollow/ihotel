import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFkCz } from '../fk-cz.model';

@Component({
  selector: 'jhi-fk-cz-detail',
  templateUrl: './fk-cz-detail.component.html',
})
export class FkCzDetailComponent implements OnInit {
  fkCz: IFkCz | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fkCz }) => {
      this.fkCz = fkCz;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
