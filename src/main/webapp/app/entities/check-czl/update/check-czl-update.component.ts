import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICheckCzl, CheckCzl } from '../check-czl.model';
import { CheckCzlService } from '../service/check-czl.service';

@Component({
  selector: 'jhi-check-czl-update',
  templateUrl: './check-czl-update.component.html',
})
export class CheckCzlUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    hoteltime: [null, [Validators.required]],
    rtype: [null, [Validators.required, Validators.maxLength(45)]],
    rnum: [null, [Validators.required]],
    rOutnum: [null, [Validators.required]],
    czl: [null, [Validators.required]],
    chagrge: [null, [Validators.required]],
    chagrgeAvg: [null, [Validators.required]],
    empn: [null, [Validators.required, Validators.maxLength(45)]],
    entertime: [null, [Validators.required]],
  });

  constructor(protected checkCzlService: CheckCzlService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkCzl }) => {
      if (checkCzl.id === undefined) {
        const today = dayjs().startOf('day');
        checkCzl.hoteltime = today;
        checkCzl.entertime = today;
      }

      this.updateForm(checkCzl);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkCzl = this.createFromForm();
    if (checkCzl.id !== undefined) {
      this.subscribeToSaveResponse(this.checkCzlService.update(checkCzl));
    } else {
      this.subscribeToSaveResponse(this.checkCzlService.create(checkCzl));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckCzl>>): void {
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

  protected updateForm(checkCzl: ICheckCzl): void {
    this.editForm.patchValue({
      id: checkCzl.id,
      hoteltime: checkCzl.hoteltime ? checkCzl.hoteltime.format(DATE_TIME_FORMAT) : null,
      rtype: checkCzl.rtype,
      rnum: checkCzl.rnum,
      rOutnum: checkCzl.rOutnum,
      czl: checkCzl.czl,
      chagrge: checkCzl.chagrge,
      chagrgeAvg: checkCzl.chagrgeAvg,
      empn: checkCzl.empn,
      entertime: checkCzl.entertime ? checkCzl.entertime.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): ICheckCzl {
    return {
      ...new CheckCzl(),
      id: this.editForm.get(['id'])!.value,
      hoteltime: this.editForm.get(['hoteltime'])!.value ? dayjs(this.editForm.get(['hoteltime'])!.value, DATE_TIME_FORMAT) : undefined,
      rtype: this.editForm.get(['rtype'])!.value,
      rnum: this.editForm.get(['rnum'])!.value,
      rOutnum: this.editForm.get(['rOutnum'])!.value,
      czl: this.editForm.get(['czl'])!.value,
      chagrge: this.editForm.get(['chagrge'])!.value,
      chagrgeAvg: this.editForm.get(['chagrgeAvg'])!.value,
      empn: this.editForm.get(['empn'])!.value,
      entertime: this.editForm.get(['entertime'])!.value ? dayjs(this.editForm.get(['entertime'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
