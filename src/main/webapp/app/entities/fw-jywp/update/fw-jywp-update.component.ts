import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IFwJywp, FwJywp } from '../fw-jywp.model';
import { FwJywpService } from '../service/fw-jywp.service';

@Component({
  selector: 'jhi-fw-jywp-update',
  templateUrl: './fw-jywp-update.component.html',
})
export class FwJywpUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    jyrq: [],
    roomn: [null, [Validators.maxLength(50)]],
    guestname: [null, [Validators.maxLength(100)]],
    jywp: [null, [Validators.maxLength(200)]],
    fwy: [null, [Validators.maxLength(100)]],
    djr: [null, [Validators.maxLength(100)]],
    flag: [null, [Validators.maxLength(2)]],
    ghrq: [],
    djrq: [],
    remark: [null, [Validators.maxLength(300)]],
  });

  constructor(protected fwJywpService: FwJywpService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fwJywp }) => {
      if (fwJywp.id === undefined) {
        const today = dayjs().startOf('day');
        fwJywp.jyrq = today;
        fwJywp.ghrq = today;
        fwJywp.djrq = today;
      }

      this.updateForm(fwJywp);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fwJywp = this.createFromForm();
    if (fwJywp.id !== undefined) {
      this.subscribeToSaveResponse(this.fwJywpService.update(fwJywp));
    } else {
      this.subscribeToSaveResponse(this.fwJywpService.create(fwJywp));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFwJywp>>): void {
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

  protected updateForm(fwJywp: IFwJywp): void {
    this.editForm.patchValue({
      id: fwJywp.id,
      jyrq: fwJywp.jyrq ? fwJywp.jyrq.format(DATE_TIME_FORMAT) : null,
      roomn: fwJywp.roomn,
      guestname: fwJywp.guestname,
      jywp: fwJywp.jywp,
      fwy: fwJywp.fwy,
      djr: fwJywp.djr,
      flag: fwJywp.flag,
      ghrq: fwJywp.ghrq ? fwJywp.ghrq.format(DATE_TIME_FORMAT) : null,
      djrq: fwJywp.djrq ? fwJywp.djrq.format(DATE_TIME_FORMAT) : null,
      remark: fwJywp.remark,
    });
  }

  protected createFromForm(): IFwJywp {
    return {
      ...new FwJywp(),
      id: this.editForm.get(['id'])!.value,
      jyrq: this.editForm.get(['jyrq'])!.value ? dayjs(this.editForm.get(['jyrq'])!.value, DATE_TIME_FORMAT) : undefined,
      roomn: this.editForm.get(['roomn'])!.value,
      guestname: this.editForm.get(['guestname'])!.value,
      jywp: this.editForm.get(['jywp'])!.value,
      fwy: this.editForm.get(['fwy'])!.value,
      djr: this.editForm.get(['djr'])!.value,
      flag: this.editForm.get(['flag'])!.value,
      ghrq: this.editForm.get(['ghrq'])!.value ? dayjs(this.editForm.get(['ghrq'])!.value, DATE_TIME_FORMAT) : undefined,
      djrq: this.editForm.get(['djrq'])!.value ? dayjs(this.editForm.get(['djrq'])!.value, DATE_TIME_FORMAT) : undefined,
      remark: this.editForm.get(['remark'])!.value,
    };
  }
}
