import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClog } from '../clog.model';

@Component({
  selector: 'jhi-clog-detail',
  templateUrl: './clog-detail.component.html',
})
export class ClogDetailComponent implements OnInit {
  clog: IClog | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clog }) => {
      this.clog = clog;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
