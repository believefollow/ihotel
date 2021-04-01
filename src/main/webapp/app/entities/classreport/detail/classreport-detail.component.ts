import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClassreport } from '../classreport.model';

@Component({
  selector: 'jhi-classreport-detail',
  templateUrl: './classreport-detail.component.html',
})
export class ClassreportDetailComponent implements OnInit {
  classreport: IClassreport | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classreport }) => {
      this.classreport = classreport;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
