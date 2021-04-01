import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICzBqz } from '../cz-bqz.model';

@Component({
  selector: 'jhi-cz-bqz-detail',
  templateUrl: './cz-bqz-detail.component.html',
})
export class CzBqzDetailComponent implements OnInit {
  czBqz: ICzBqz | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ czBqz }) => {
      this.czBqz = czBqz;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
