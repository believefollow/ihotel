import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClassRename } from '../class-rename.model';

@Component({
  selector: 'jhi-class-rename-detail',
  templateUrl: './class-rename-detail.component.html',
})
export class ClassRenameDetailComponent implements OnInit {
  classRename: IClassRename | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classRename }) => {
      this.classRename = classRename;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
