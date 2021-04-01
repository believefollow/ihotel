import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICrinfo } from '../crinfo.model';

@Component({
  selector: 'jhi-crinfo-detail',
  templateUrl: './crinfo-detail.component.html',
})
export class CrinfoDetailComponent implements OnInit {
  crinfo: ICrinfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ crinfo }) => {
      this.crinfo = crinfo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
