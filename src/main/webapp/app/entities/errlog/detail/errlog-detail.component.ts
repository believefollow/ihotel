import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IErrlog } from '../errlog.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-errlog-detail',
  templateUrl: './errlog-detail.component.html',
})
export class ErrlogDetailComponent implements OnInit {
  errlog: IErrlog | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ errlog }) => {
      this.errlog = errlog;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
