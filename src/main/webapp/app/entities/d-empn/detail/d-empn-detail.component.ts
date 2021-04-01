import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDEmpn } from '../d-empn.model';

@Component({
  selector: 'jhi-d-empn-detail',
  templateUrl: './d-empn-detail.component.html',
})
export class DEmpnDetailComponent implements OnInit {
  dEmpn: IDEmpn | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dEmpn }) => {
      this.dEmpn = dEmpn;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
