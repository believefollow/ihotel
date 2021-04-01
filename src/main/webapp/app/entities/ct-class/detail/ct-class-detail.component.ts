import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICtClass } from '../ct-class.model';

@Component({
  selector: 'jhi-ct-class-detail',
  templateUrl: './ct-class-detail.component.html',
})
export class CtClassDetailComponent implements OnInit {
  ctClass: ICtClass | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ctClass }) => {
      this.ctClass = ctClass;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
