import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IAuditinfo, Auditinfo } from '../auditinfo.model';
import { AuditinfoService } from '../service/auditinfo.service';

@Component({
  selector: 'jhi-auditinfo-update',
  templateUrl: './auditinfo-update.component.html',
})
export class AuditinfoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    auditdate: [null, [Validators.required]],
    audittime: [],
    empn: [null, [Validators.maxLength(10)]],
    aidentify: [null, [Validators.maxLength(100)]],
  });

  constructor(protected auditinfoService: AuditinfoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditinfo }) => {
      if (auditinfo.id === undefined) {
        const today = dayjs().startOf('day');
        auditinfo.auditdate = today;
        auditinfo.audittime = today;
      }

      this.updateForm(auditinfo);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const auditinfo = this.createFromForm();
    if (auditinfo.id !== undefined) {
      this.subscribeToSaveResponse(this.auditinfoService.update(auditinfo));
    } else {
      this.subscribeToSaveResponse(this.auditinfoService.create(auditinfo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuditinfo>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(auditinfo: IAuditinfo): void {
    this.editForm.patchValue({
      id: auditinfo.id,
      auditdate: auditinfo.auditdate ? auditinfo.auditdate.format(DATE_TIME_FORMAT) : null,
      audittime: auditinfo.audittime ? auditinfo.audittime.format(DATE_TIME_FORMAT) : null,
      empn: auditinfo.empn,
      aidentify: auditinfo.aidentify,
    });
  }

  protected createFromForm(): IAuditinfo {
    return {
      ...new Auditinfo(),
      id: this.editForm.get(['id'])!.value,
      auditdate: this.editForm.get(['auditdate'])!.value ? dayjs(this.editForm.get(['auditdate'])!.value, DATE_TIME_FORMAT) : undefined,
      audittime: this.editForm.get(['audittime'])!.value ? dayjs(this.editForm.get(['audittime'])!.value, DATE_TIME_FORMAT) : undefined,
      empn: this.editForm.get(['empn'])!.value,
      aidentify: this.editForm.get(['aidentify'])!.value,
    };
  }
}
