import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccbillno } from '../accbillno.model';

@Component({
  selector: 'jhi-accbillno-detail',
  templateUrl: './accbillno-detail.component.html',
})
export class AccbillnoDetailComponent implements OnInit {
  accbillno: IAccbillno | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accbillno }) => {
      this.accbillno = accbillno;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
