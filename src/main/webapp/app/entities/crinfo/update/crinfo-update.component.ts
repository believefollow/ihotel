import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICrinfo, Crinfo } from '../crinfo.model';
import { CrinfoService } from '../service/crinfo.service';

@Component({
  selector: 'jhi-crinfo-update',
  templateUrl: './crinfo-update.component.html',
})
export class CrinfoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    operatetime: [],
    oldrent: [],
    newrent: [],
    oldroomn: [null, [Validators.maxLength(10)]],
    newroomn: [null, [Validators.maxLength(10)]],
    account: [null, [Validators.maxLength(30)]],
    empn: [null, [Validators.maxLength(10)]],
    oldday: [],
    newday: [],
    hoteltime: [],
    roomn: [null, [Validators.maxLength(10)]],
    memo: [null, [Validators.required, Validators.maxLength(100)]],
    realname: [null, [Validators.maxLength(50)]],
    isup: [],
  });

  constructor(protected crinfoService: CrinfoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ crinfo }) => {
      if (crinfo.id === undefined) {
        const today = dayjs().startOf('day');
        crinfo.operatetime = today;
        crinfo.hoteltime = today;
      }

      this.updateForm(crinfo);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const crinfo = this.createFromForm();
    if (crinfo.id !== undefined) {
      this.subscribeToSaveResponse(this.crinfoService.update(crinfo));
    } else {
      this.subscribeToSaveResponse(this.crinfoService.create(crinfo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICrinfo>>): void {
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

  protected updateForm(crinfo: ICrinfo): void {
    this.editForm.patchValue({
      id: crinfo.id,
      operatetime: crinfo.operatetime ? crinfo.operatetime.format(DATE_TIME_FORMAT) : null,
      oldrent: crinfo.oldrent,
      newrent: crinfo.newrent,
      oldroomn: crinfo.oldroomn,
      newroomn: crinfo.newroomn,
      account: crinfo.account,
      empn: crinfo.empn,
      oldday: crinfo.oldday,
      newday: crinfo.newday,
      hoteltime: crinfo.hoteltime ? crinfo.hoteltime.format(DATE_TIME_FORMAT) : null,
      roomn: crinfo.roomn,
      memo: crinfo.memo,
      realname: crinfo.realname,
      isup: crinfo.isup,
    });
  }

  protected createFromForm(): ICrinfo {
    return {
      ...new Crinfo(),
      id: this.editForm.get(['id'])!.value,
      operatetime: this.editForm.get(['operatetime'])!.value
        ? dayjs(this.editForm.get(['operatetime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      oldrent: this.editForm.get(['oldrent'])!.value,
      newrent: this.editForm.get(['newrent'])!.value,
      oldroomn: this.editForm.get(['oldroomn'])!.value,
      newroomn: this.editForm.get(['newroomn'])!.value,
      account: this.editForm.get(['account'])!.value,
      empn: this.editForm.get(['empn'])!.value,
      oldday: this.editForm.get(['oldday'])!.value,
      newday: this.editForm.get(['newday'])!.value,
      hoteltime: this.editForm.get(['hoteltime'])!.value ? dayjs(this.editForm.get(['hoteltime'])!.value, DATE_TIME_FORMAT) : undefined,
      roomn: this.editForm.get(['roomn'])!.value,
      memo: this.editForm.get(['memo'])!.value,
      realname: this.editForm.get(['realname'])!.value,
      isup: this.editForm.get(['isup'])!.value,
    };
  }
}
