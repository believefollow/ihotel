import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAuditinfo } from '../auditinfo.model';

@Component({
  selector: 'jhi-auditinfo-detail',
  templateUrl: './auditinfo-detail.component.html',
})
export class AuditinfoDetailComponent implements OnInit {
  auditinfo: IAuditinfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditinfo }) => {
      this.auditinfo = auditinfo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
