import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICheckinTz, CheckinTz } from '../checkin-tz.model';
import { CheckinTzService } from '../service/checkin-tz.service';

@Component({
  selector: 'jhi-checkin-tz-update',
  templateUrl: './checkin-tz-update.component.html',
})
export class CheckinTzUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    guestId: [],
    account: [null, [Validators.maxLength(30)]],
    hoteltime: [],
    indatetime: [],
    residefate: [],
    gotime: [],
    empn: [null, [Validators.maxLength(10)]],
    roomn: [null, [Validators.required, Validators.maxLength(10)]],
    rentp: [null, [Validators.required, Validators.maxLength(10)]],
    protocolrent: [],
    remark: [null, [Validators.maxLength(8000)]],
    phonen: [null, [Validators.maxLength(10)]],
    empn2: [null, [Validators.maxLength(10)]],
    memo: [null, [Validators.maxLength(500)]],
    lfSign: [null, [Validators.maxLength(1)]],
    guestname: [null, [Validators.maxLength(45)]],
    bc: [null, [Validators.maxLength(50)]],
    roomtype: [null, [Validators.maxLength(50)]],
  });

  constructor(protected checkinTzService: CheckinTzService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkinTz }) => {
      if (checkinTz.id === undefined) {
        const today = dayjs().startOf('day');
        checkinTz.hoteltime = today;
        checkinTz.indatetime = today;
        checkinTz.gotime = today;
      }

      this.updateForm(checkinTz);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkinTz = this.createFromForm();
    if (checkinTz.id !== undefined) {
      this.subscribeToSaveResponse(this.checkinTzService.update(checkinTz));
    } else {
      this.subscribeToSaveResponse(this.checkinTzService.create(checkinTz));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckinTz>>): void {
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

  protected updateForm(checkinTz: ICheckinTz): void {
    this.editForm.patchValue({
      id: checkinTz.id,
      guestId: checkinTz.guestId,
      account: checkinTz.account,
      hoteltime: checkinTz.hoteltime ? checkinTz.hoteltime.format(DATE_TIME_FORMAT) : null,
      indatetime: checkinTz.indatetime ? checkinTz.indatetime.format(DATE_TIME_FORMAT) : null,
      residefate: checkinTz.residefate,
      gotime: checkinTz.gotime ? checkinTz.gotime.format(DATE_TIME_FORMAT) : null,
      empn: checkinTz.empn,
      roomn: checkinTz.roomn,
      rentp: checkinTz.rentp,
      protocolrent: checkinTz.protocolrent,
      remark: checkinTz.remark,
      phonen: checkinTz.phonen,
      empn2: checkinTz.empn2,
      memo: checkinTz.memo,
      lfSign: checkinTz.lfSign,
      guestname: checkinTz.guestname,
      bc: checkinTz.bc,
      roomtype: checkinTz.roomtype,
    });
  }

  protected createFromForm(): ICheckinTz {
    return {
      ...new CheckinTz(),
      id: this.editForm.get(['id'])!.value,
      guestId: this.editForm.get(['guestId'])!.value,
      account: this.editForm.get(['account'])!.value,
      hoteltime: this.editForm.get(['hoteltime'])!.value ? dayjs(this.editForm.get(['hoteltime'])!.value, DATE_TIME_FORMAT) : undefined,
      indatetime: this.editForm.get(['indatetime'])!.value ? dayjs(this.editForm.get(['indatetime'])!.value, DATE_TIME_FORMAT) : undefined,
      residefate: this.editForm.get(['residefate'])!.value,
      gotime: this.editForm.get(['gotime'])!.value ? dayjs(this.editForm.get(['gotime'])!.value, DATE_TIME_FORMAT) : undefined,
      empn: this.editForm.get(['empn'])!.value,
      roomn: this.editForm.get(['roomn'])!.value,
      rentp: this.editForm.get(['rentp'])!.value,
      protocolrent: this.editForm.get(['protocolrent'])!.value,
      remark: this.editForm.get(['remark'])!.value,
      phonen: this.editForm.get(['phonen'])!.value,
      empn2: this.editForm.get(['empn2'])!.value,
      memo: this.editForm.get(['memo'])!.value,
      lfSign: this.editForm.get(['lfSign'])!.value,
      guestname: this.editForm.get(['guestname'])!.value,
      bc: this.editForm.get(['bc'])!.value,
      roomtype: this.editForm.get(['roomtype'])!.value,
    };
  }
}
