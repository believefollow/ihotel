import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDayearndetail, Dayearndetail } from '../dayearndetail.model';
import { DayearndetailService } from '../service/dayearndetail.service';

@Component({
  selector: 'jhi-dayearndetail-update',
  templateUrl: './dayearndetail-update.component.html',
})
export class DayearndetailUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    earndate: [null, [Validators.required]],
    salespotn: [null, [Validators.required]],
    money: [],
  });

  constructor(protected dayearndetailService: DayearndetailService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dayearndetail }) => {
      if (dayearndetail.id === undefined) {
        const today = dayjs().startOf('day');
        dayearndetail.earndate = today;
      }

      this.updateForm(dayearndetail);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dayearndetail = this.createFromForm();
    if (dayearndetail.id !== undefined) {
      this.subscribeToSaveResponse(this.dayearndetailService.update(dayearndetail));
    } else {
      this.subscribeToSaveResponse(this.dayearndetailService.create(dayearndetail));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDayearndetail>>): void {
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

  protected updateForm(dayearndetail: IDayearndetail): void {
    this.editForm.patchValue({
      id: dayearndetail.id,
      earndate: dayearndetail.earndate ? dayearndetail.earndate.format(DATE_TIME_FORMAT) : null,
      salespotn: dayearndetail.salespotn,
      money: dayearndetail.money,
    });
  }

  protected createFromForm(): IDayearndetail {
    return {
      ...new Dayearndetail(),
      id: this.editForm.get(['id'])!.value,
      earndate: this.editForm.get(['earndate'])!.value ? dayjs(this.editForm.get(['earndate'])!.value, DATE_TIME_FORMAT) : undefined,
      salespotn: this.editForm.get(['salespotn'])!.value,
      money: this.editForm.get(['money'])!.value,
    };
  }
}
