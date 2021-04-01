import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDXs, DXs } from '../d-xs.model';
import { DXsService } from '../service/d-xs.service';

@Component({
  selector: 'jhi-d-xs-update',
  templateUrl: './d-xs-update.component.html',
})
export class DXsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    begintime: [null, [Validators.required]],
    endtime: [null, [Validators.required]],
    ckbillno: [null, [Validators.required, Validators.maxLength(30)]],
    depot: [null, [Validators.maxLength(50)]],
    kcid: [null, [Validators.required]],
    ckid: [null, [Validators.required]],
    spbm: [null, [Validators.required, Validators.maxLength(30)]],
    spmc: [null, [Validators.required, Validators.maxLength(50)]],
    unit: [null, [Validators.maxLength(20)]],
    rkprice: [],
    xsprice: [],
    sl: [],
    rkje: [],
    xsje: [],
  });

  constructor(protected dXsService: DXsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dXs }) => {
      if (dXs.id === undefined) {
        const today = dayjs().startOf('day');
        dXs.begintime = today;
        dXs.endtime = today;
      }

      this.updateForm(dXs);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dXs = this.createFromForm();
    if (dXs.id !== undefined) {
      this.subscribeToSaveResponse(this.dXsService.update(dXs));
    } else {
      this.subscribeToSaveResponse(this.dXsService.create(dXs));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDXs>>): void {
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

  protected updateForm(dXs: IDXs): void {
    this.editForm.patchValue({
      id: dXs.id,
      begintime: dXs.begintime ? dXs.begintime.format(DATE_TIME_FORMAT) : null,
      endtime: dXs.endtime ? dXs.endtime.format(DATE_TIME_FORMAT) : null,
      ckbillno: dXs.ckbillno,
      depot: dXs.depot,
      kcid: dXs.kcid,
      ckid: dXs.ckid,
      spbm: dXs.spbm,
      spmc: dXs.spmc,
      unit: dXs.unit,
      rkprice: dXs.rkprice,
      xsprice: dXs.xsprice,
      sl: dXs.sl,
      rkje: dXs.rkje,
      xsje: dXs.xsje,
    });
  }

  protected createFromForm(): IDXs {
    return {
      ...new DXs(),
      id: this.editForm.get(['id'])!.value,
      begintime: this.editForm.get(['begintime'])!.value ? dayjs(this.editForm.get(['begintime'])!.value, DATE_TIME_FORMAT) : undefined,
      endtime: this.editForm.get(['endtime'])!.value ? dayjs(this.editForm.get(['endtime'])!.value, DATE_TIME_FORMAT) : undefined,
      ckbillno: this.editForm.get(['ckbillno'])!.value,
      depot: this.editForm.get(['depot'])!.value,
      kcid: this.editForm.get(['kcid'])!.value,
      ckid: this.editForm.get(['ckid'])!.value,
      spbm: this.editForm.get(['spbm'])!.value,
      spmc: this.editForm.get(['spmc'])!.value,
      unit: this.editForm.get(['unit'])!.value,
      rkprice: this.editForm.get(['rkprice'])!.value,
      xsprice: this.editForm.get(['xsprice'])!.value,
      sl: this.editForm.get(['sl'])!.value,
      rkje: this.editForm.get(['rkje'])!.value,
      xsje: this.editForm.get(['xsje'])!.value,
    };
  }
}
