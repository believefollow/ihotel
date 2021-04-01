import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFeetype } from '../feetype.model';

@Component({
  selector: 'jhi-feetype-detail',
  templateUrl: './feetype-detail.component.html',
})
export class FeetypeDetailComponent implements OnInit {
  feetype: IFeetype | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feetype }) => {
      this.feetype = feetype;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
