import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICheckCzl2, CheckCzl2 } from '../check-czl-2.model';
import { CheckCzl2Service } from '../service/check-czl-2.service';

@Component({
  selector: 'jhi-check-czl-2-update',
  templateUrl: './check-czl-2-update.component.html',
})
export class CheckCzl2UpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    hoteltime: [null, [Validators.required]],
    protocol: [null, [Validators.required, Validators.maxLength(45)]],
    rnum: [null, [Validators.required]],
    czl: [null, [Validators.required]],
    chagrge: [null, [Validators.required]],
    chagrgeAvg: [null, [Validators.required]],
    empn: [null, [Validators.required, Validators.maxLength(45)]],
    entertime: [null, [Validators.required]],
  });

  constructor(protected checkCzl2Service: CheckCzl2Service, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkCzl2 }) => {
      if (checkCzl2.id === undefined) {
        const today = dayjs().startOf('day');
        checkCzl2.hoteltime = today;
        checkCzl2.entertime = today;
      }

      this.updateForm(checkCzl2);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkCzl2 = this.createFromForm();
    if (checkCzl2.id !== undefined) {
      this.subscribeToSaveResponse(this.checkCzl2Service.update(checkCzl2));
    } else {
      this.subscribeToSaveResponse(this.checkCzl2Service.create(checkCzl2));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckCzl2>>): void {
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

  protected updateForm(checkCzl2: ICheckCzl2): void {
    this.editForm.patchValue({
      id: checkCzl2.id,
      hoteltime: checkCzl2.hoteltime ? checkCzl2.hoteltime.format(DATE_TIME_FORMAT) : null,
      protocol: checkCzl2.protocol,
      rnum: checkCzl2.rnum,
      czl: checkCzl2.czl,
      chagrge: checkCzl2.chagrge,
      chagrgeAvg: checkCzl2.chagrgeAvg,
      empn: checkCzl2.empn,
      entertime: checkCzl2.entertime ? checkCzl2.entertime.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ICheckCzl2 {
    return {
      ...new CheckCzl2(),
      id: this.editForm.get(['id'])!.value,
      hoteltime: this.editForm.get(['hoteltime'])!.value ? dayjs(this.editForm.get(['hoteltime'])!.value, DATE_TIME_FORMAT) : undefined,
      protocol: this.editForm.get(['protocol'])!.value,
      rnum: this.editForm.get(['rnum'])!.value,
      czl: this.editForm.get(['czl'])!.value,
      chagrge: this.editForm.get(['chagrge'])!.value,
      chagrgeAvg: this.editForm.get(['chagrgeAvg'])!.value,
      empn: this.editForm.get(['empn'])!.value,
      entertime: this.editForm.get(['entertime'])!.value ? dayjs(this.editForm.get(['entertime'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
