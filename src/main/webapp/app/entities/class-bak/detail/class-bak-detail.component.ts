import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClassBak } from '../class-bak.model';

@Component({
  selector: 'jhi-class-bak-detail',
  templateUrl: './class-bak-detail.component.html',
})
export class ClassBakDetailComponent implements OnInit {
  classBak: IClassBak | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classBak }) => {
      this.classBak = classBak;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
