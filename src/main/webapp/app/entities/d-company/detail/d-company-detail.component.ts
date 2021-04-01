import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDCompany } from '../d-company.model';

@Component({
  selector: 'jhi-d-company-detail',
  templateUrl: './d-company-detail.component.html',
})
export class DCompanyDetailComponent implements OnInit {
  dCompany: IDCompany | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dCompany }) => {
      this.dCompany = dCompany;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
