import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDxSedinfo } from '../dx-sedinfo.model';

@Component({
  selector: 'jhi-dx-sedinfo-detail',
  templateUrl: './dx-sedinfo-detail.component.html',
})
export class DxSedinfoDetailComponent implements OnInit {
  dxSedinfo: IDxSedinfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dxSedinfo }) => {
      this.dxSedinfo = dxSedinfo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
