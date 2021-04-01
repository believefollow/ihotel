import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICk2xsy } from '../ck-2-xsy.model';

@Component({
  selector: 'jhi-ck-2-xsy-detail',
  templateUrl: './ck-2-xsy-detail.component.html',
})
export class Ck2xsyDetailComponent implements OnInit {
  ck2xsy: ICk2xsy | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ck2xsy }) => {
      this.ck2xsy = ck2xsy;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
