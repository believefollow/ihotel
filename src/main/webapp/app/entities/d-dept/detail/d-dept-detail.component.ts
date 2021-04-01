import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDDept } from '../d-dept.model';

@Component({
  selector: 'jhi-d-dept-detail',
  templateUrl: './d-dept-detail.component.html',
})
export class DDeptDetailComponent implements OnInit {
  dDept: IDDept | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dDept }) => {
      this.dDept = dDept;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
