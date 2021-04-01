import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IClog, Clog } from '../clog.model';
import { ClogService } from '../service/clog.service';

@Component({
  selector: 'jhi-clog-update',
  templateUrl: './clog-update.component.html',
})
export class ClogUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    empn: [null, [Validators.maxLength(20)]],
    begindate: [],
    enddate: [],
    dqrq: [],
  });

  constructor(protected clogService: ClogService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clog }) => {
      if (clog.id === undefined) {
        const today = dayjs().startOf('day');
        clog.begindate = today;
        clog.enddate = today;
        clog.dqrq = today;
      }

      this.updateForm(clog);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const clog = this.createFromForm();
    if (clog.id !== undefined) {
      this.subscribeToSaveResponse(this.clogService.update(clog));
    } else {
      this.subscribeToSaveResponse(this.clogService.create(clog));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClog>>): void {
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

  protected updateForm(clog: IClog): void {
    this.editForm.patchValue({
      id: clog.id,
      empn: clog.empn,
      begindate: clog.begindate ? clog.begindate.format(DATE_TIME_FORMAT) : null,
      enddate: clog.enddate ? clog.enddate.format(DATE_TIME_FORMAT) : null,
      dqrq: clog.dqrq ? clog.dqrq.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IClog {
    return {
      ...new Clog(),
      id: this.editForm.get(['id'])!.value,
      empn: this.editForm.get(['empn'])!.value,
      begindate: this.editForm.get(['begindate'])!.value ? dayjs(this.editForm.get(['begindate'])!.value, DATE_TIME_FORMAT) : undefined,
      enddate: this.editForm.get(['enddate'])!.value ? dayjs(this.editForm.get(['enddate'])!.value, DATE_TIME_FORMAT) : undefined,
      dqrq: this.editForm.get(['dqrq'])!.value ? dayjs(this.editForm.get(['dqrq'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
