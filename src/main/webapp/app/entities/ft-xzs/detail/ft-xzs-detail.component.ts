import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFtXzs } from '../ft-xzs.model';

@Component({
  selector: 'jhi-ft-xzs-detail',
  templateUrl: './ft-xzs-detail.component.html',
})
export class FtXzsDetailComponent implements OnInit {
  ftXzs: IFtXzs | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ftXzs }) => {
      this.ftXzs = ftXzs;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
