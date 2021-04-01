import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFtXz } from '../ft-xz.model';

@Component({
  selector: 'jhi-ft-xz-detail',
  templateUrl: './ft-xz-detail.component.html',
})
export class FtXzDetailComponent implements OnInit {
  ftXz: IFtXz | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ftXz }) => {
      this.ftXz = ftXz;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
