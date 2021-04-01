import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IFwDs, FwDs } from '../fw-ds.model';
import { FwDsService } from '../service/fw-ds.service';

@Component({
  selector: 'jhi-fw-ds-update',
  templateUrl: './fw-ds-update.component.html',
})
export class FwDsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    hoteltime: [],
    rq: [],
    xz: [],
    memo: [null, [Validators.maxLength(50)]],
    fwy: [null, [Validators.maxLength(50)]],
    roomn: [null, [Validators.maxLength(50)]],
    rtype: [null, [Validators.maxLength(100)]],
    empn: [null, [Validators.maxLength(100)]],
    sl: [],
  });

  constructor(protected fwDsService: FwDsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fwDs }) => {
      if (fwDs.id === undefined) {
        const today = dayjs().startOf('day');
        fwDs.hoteltime = today;
        fwDs.rq = today;
      }

      this.updateForm(fwDs);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fwDs = this.createFromForm();
    if (fwDs.id !== undefined) {
      this.subscribeToSaveResponse(this.fwDsService.update(fwDs));
    } else {
      this.subscribeToSaveResponse(this.fwDsService.create(fwDs));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFwDs>>): void {
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

  protected updateForm(fwDs: IFwDs): void {
    this.editForm.patchValue({
      id: fwDs.id,
      hoteltime: fwDs.hoteltime ? fwDs.hoteltime.format(DATE_TIME_FORMAT) : null,
      rq: fwDs.rq ? fwDs.rq.format(DATE_TIME_FORMAT) : null,
      xz: fwDs.xz,
      memo: fwDs.memo,
      fwy: fwDs.fwy,
      roomn: fwDs.roomn,
      rtype: fwDs.rtype,
      empn: fwDs.empn,
      sl: fwDs.sl,
    });
  }

  protected createFromForm(): IFwDs {
    return {
      ...new FwDs(),
      id: this.editForm.get(['id'])!.value,
      hoteltime: this.editForm.get(['hoteltime'])!.value ? dayjs(this.editForm.get(['hoteltime'])!.value, DATE_TIME_FORMAT) : undefined,
      rq: this.editForm.get(['rq'])!.value ? dayjs(this.editForm.get(['rq'])!.value, DATE_TIME_FORMAT) : undefined,
      xz: this.editForm.get(['xz'])!.value,
      memo: this.editForm.get(['memo'])!.value,
      fwy: this.editForm.get(['fwy'])!.value,
      roomn: this.editForm.get(['roomn'])!.value,
      rtype: this.editForm.get(['rtype'])!.value,
      empn: this.editForm.get(['empn'])!.value,
      sl: this.editForm.get(['sl'])!.value,
    };
  }
}
