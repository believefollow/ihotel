import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICode1 } from '../code-1.model';

@Component({
  selector: 'jhi-code-1-detail',
  templateUrl: './code-1-detail.component.html',
})
export class Code1DetailComponent implements OnInit {
  code1: ICode1 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ code1 }) => {
      this.code1 = code1;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
