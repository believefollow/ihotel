import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IFwWxf, FwWxf } from '../fw-wxf.model';
import { FwWxfService } from '../service/fw-wxf.service';

@Component({
  selector: 'jhi-fw-wxf-update',
  templateUrl: './fw-wxf-update.component.html',
})
export class FwWxfUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    roomn: [null, [Validators.maxLength(100)]],
    memo: [null, [Validators.maxLength(200)]],
    djrq: [],
    wxr: [null, [Validators.maxLength(100)]],
    wcrq: [],
    djr: [null, [Validators.maxLength(100)]],
    flag: [null, [Validators.maxLength(10)]],
  });

  constructor(protected fwWxfService: FwWxfService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fwWxf }) => {
      if (fwWxf.id === undefined) {
        const today = dayjs().startOf('day');
        fwWxf.djrq = today;
        fwWxf.wcrq = today;
      }

      this.updateForm(fwWxf);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fwWxf = this.createFromForm();
    if (fwWxf.id !== undefined) {
      this.subscribeToSaveResponse(this.fwWxfService.update(fwWxf));
    } else {
      this.subscribeToSaveResponse(this.fwWxfService.create(fwWxf));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFwWxf>>): void {
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

  protected updateForm(fwWxf: IFwWxf): void {
    this.editForm.patchValue({
      id: fwWxf.id,
      roomn: fwWxf.roomn,
      memo: fwWxf.memo,
      djrq: fwWxf.djrq ? fwWxf.djrq.format(DATE_TIME_FORMAT) : null,
      wxr: fwWxf.wxr,
      wcrq: fwWxf.wcrq ? fwWxf.wcrq.format(DATE_TIME_FORMAT) : null,
      djr: fwWxf.djr,
      flag: fwWxf.flag,
    });
  }

  protected createFromForm(): IFwWxf {
    return {
      ...new FwWxf(),
      id: this.editForm.get(['id'])!.value,
      roomn: this.editForm.get(['roomn'])!.value,
      memo: this.editForm.get(['memo'])!.value,
      djrq: this.editForm.get(['djrq'])!.value ? dayjs(this.editForm.get(['djrq'])!.value, DATE_TIME_FORMAT) : undefined,
      wxr: this.editForm.get(['wxr'])!.value,
      wcrq: this.editForm.get(['wcrq'])!.value ? dayjs(this.editForm.get(['wcrq'])!.value, DATE_TIME_FORMAT) : undefined,
      djr: this.editForm.get(['djr'])!.value,
      flag: this.editForm.get(['flag'])!.value,
    };
  }
}
