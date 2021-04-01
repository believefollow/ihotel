import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IFwYlwp, FwYlwp } from '../fw-ylwp.model';
import { FwYlwpService } from '../service/fw-ylwp.service';

@Component({
  selector: 'jhi-fw-ylwp-update',
  templateUrl: './fw-ylwp-update.component.html',
})
export class FwYlwpUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    roomn: [null, [Validators.maxLength(50)]],
    guestname: [null, [Validators.maxLength(100)]],
    memo: [null, [Validators.maxLength(300)]],
    sdr: [null, [Validators.maxLength(100)]],
    sdrq: [],
    rlr: [null, [Validators.maxLength(100)]],
    rlrq: [],
    remark: [null, [Validators.maxLength(300)]],
    empn: [null, [Validators.maxLength(100)]],
    czrq: [],
    flag: [null, [Validators.maxLength(2)]],
  });

  constructor(protected fwYlwpService: FwYlwpService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fwYlwp }) => {
      if (fwYlwp.id === undefined) {
        const today = dayjs().startOf('day');
        fwYlwp.sdrq = today;
        fwYlwp.rlrq = today;
        fwYlwp.czrq = today;
      }

      this.updateForm(fwYlwp);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fwYlwp = this.createFromForm();
    if (fwYlwp.id !== undefined) {
      this.subscribeToSaveResponse(this.fwYlwpService.update(fwYlwp));
    } else {
      this.subscribeToSaveResponse(this.fwYlwpService.create(fwYlwp));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFwYlwp>>): void {
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

  protected updateForm(fwYlwp: IFwYlwp): void {
    this.editForm.patchValue({
      id: fwYlwp.id,
      roomn: fwYlwp.roomn,
      guestname: fwYlwp.guestname,
      memo: fwYlwp.memo,
      sdr: fwYlwp.sdr,
      sdrq: fwYlwp.sdrq ? fwYlwp.sdrq.format(DATE_TIME_FORMAT) : null,
      rlr: fwYlwp.rlr,
      rlrq: fwYlwp.rlrq ? fwYlwp.rlrq.format(DATE_TIME_FORMAT) : null,
      remark: fwYlwp.remark,
      empn: fwYlwp.empn,
      czrq: fwYlwp.czrq ? fwYlwp.czrq.format(DATE_TIME_FORMAT) : null,
      flag: fwYlwp.flag,
    });
  }

  protected createFromForm(): IFwYlwp {
    return {
      ...new FwYlwp(),
      id: this.editForm.get(['id'])!.value,
      roomn: this.editForm.get(['roomn'])!.value,
      guestname: this.editForm.get(['guestname'])!.value,
      memo: this.editForm.get(['memo'])!.value,
      sdr: this.editForm.get(['sdr'])!.value,
      sdrq: this.editForm.get(['sdrq'])!.value ? dayjs(this.editForm.get(['sdrq'])!.value, DATE_TIME_FORMAT) : undefined,
      rlr: this.editForm.get(['rlr'])!.value,
      rlrq: this.editForm.get(['rlrq'])!.value ? dayjs(this.editForm.get(['rlrq'])!.value, DATE_TIME_FORMAT) : undefined,
      remark: this.editForm.get(['remark'])!.value,
      empn: this.editForm.get(['empn'])!.value,
      czrq: this.editForm.get(['czrq'])!.value ? dayjs(this.editForm.get(['czrq'])!.value, DATE_TIME_FORMAT) : undefined,
      flag: this.editForm.get(['flag'])!.value,
    };
  }
}
