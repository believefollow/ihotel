import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDCktime, DCktime } from '../d-cktime.model';
import { DCktimeService } from '../service/d-cktime.service';

@Component({
  selector: 'jhi-d-cktime-update',
  templateUrl: './d-cktime-update.component.html',
})
export class DCktimeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    begintime: [null, [Validators.required]],
    endtime: [null, [Validators.required]],
    depot: [null, [Validators.required, Validators.maxLength(20)]],
    ckbillno: [null, [Validators.maxLength(30)]],
  });

  constructor(protected dCktimeService: DCktimeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dCktime }) => {
      if (dCktime.id === undefined) {
        const today = dayjs().startOf('day');
        dCktime.begintime = today;
        dCktime.endtime = today;
      }

      this.updateForm(dCktime);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dCktime = this.createFromForm();
    if (dCktime.id !== undefined) {
      this.subscribeToSaveResponse(this.dCktimeService.update(dCktime));
    } else {
      this.subscribeToSaveResponse(this.dCktimeService.create(dCktime));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDCktime>>): void {
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

  protected updateForm(dCktime: IDCktime): void {
    this.editForm.patchValue({
      id: dCktime.id,
      begintime: dCktime.begintime ? dCktime.begintime.format(DATE_TIME_FORMAT) : null,
      endtime: dCktime.endtime ? dCktime.endtime.format(DATE_TIME_FORMAT) : null,
      depot: dCktime.depot,
      ckbillno: dCktime.ckbillno,
    });
  }

  protected createFromForm(): IDCktime {
    return {
      ...new DCktime(),
      id: this.editForm.get(['id'])!.value,
      begintime: this.editForm.get(['begintime'])!.value ? dayjs(this.editForm.get(['begintime'])!.value, DATE_TIME_FORMAT) : undefined,
      endtime: this.editForm.get(['endtime'])!.value ? dayjs(this.editForm.get(['endtime'])!.value, DATE_TIME_FORMAT) : undefined,
      depot: this.editForm.get(['depot'])!.value,
      ckbillno: this.editForm.get(['ckbillno'])!.value,
    };
  }
}
