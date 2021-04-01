import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICheckinBak, CheckinBak } from '../checkin-bak.model';
import { CheckinBakService } from '../service/checkin-bak.service';

@Component({
  selector: 'jhi-checkin-bak-update',
  templateUrl: './checkin-bak-update.component.html',
})
export class CheckinBakUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    guestId: [null, [Validators.required]],
    account: [null, [Validators.required, Validators.maxLength(30)]],
    hoteltime: [null, [Validators.required]],
    indatetime: [],
    residefate: [],
    gotime: [],
    empn: [null, [Validators.maxLength(10)]],
    roomn: [null, [Validators.maxLength(300)]],
    uname: [null, [Validators.maxLength(50)]],
    rentp: [null, [Validators.required, Validators.maxLength(10)]],
    protocolrent: [],
    remark: [null, [Validators.maxLength(8000)]],
    comeinfo: [null, [Validators.maxLength(40)]],
    goinfo: [null, [Validators.maxLength(40)]],
    phonen: [null, [Validators.maxLength(10)]],
    empn2: [null, [Validators.maxLength(10)]],
    adhoc: [null, [Validators.maxLength(40)]],
    auditflag: [],
    groupn: [null, [Validators.maxLength(20)]],
    payment: [null, [Validators.maxLength(10)]],
    mtype: [null, [Validators.maxLength(10)]],
    memo: [null, [Validators.maxLength(8000)]],
    flight: [null, [Validators.maxLength(20)]],
    credit: [],
    talklevel: [null, [Validators.maxLength(10)]],
    lfSign: [null, [Validators.maxLength(1)]],
    keynum: [null, [Validators.maxLength(3)]],
    icNum: [],
    bh: [],
    icOwner: [null, [Validators.maxLength(50)]],
    markId: [null, [Validators.maxLength(50)]],
    gj: [null, [Validators.maxLength(50)]],
    yfj: [],
    hoteldate: [],
    id: [null, [Validators.required]],
  });

  constructor(protected checkinBakService: CheckinBakService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkinBak }) => {
      if (checkinBak.id === undefined) {
        const today = dayjs().startOf('day');
        checkinBak.hoteltime = today;
        checkinBak.indatetime = today;
        checkinBak.gotime = today;
        checkinBak.hoteldate = today;
      }

      this.updateForm(checkinBak);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkinBak = this.createFromForm();
    if (checkinBak.id !== undefined) {
      this.subscribeToSaveResponse(this.checkinBakService.update(checkinBak));
    } else {
      this.subscribeToSaveResponse(this.checkinBakService.create(checkinBak));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckinBak>>): void {
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

  protected updateForm(checkinBak: ICheckinBak): void {
    this.editForm.patchValue({
      guestId: checkinBak.guestId,
      account: checkinBak.account,
      hoteltime: checkinBak.hoteltime ? checkinBak.hoteltime.format(DATE_TIME_FORMAT) : null,
      indatetime: checkinBak.indatetime ? checkinBak.indatetime.format(DATE_TIME_FORMAT) : null,
      residefate: checkinBak.residefate,
      gotime: checkinBak.gotime ? checkinBak.gotime.format(DATE_TIME_FORMAT) : null,
      empn: checkinBak.empn,
      roomn: checkinBak.roomn,
      uname: checkinBak.uname,
      rentp: checkinBak.rentp,
      protocolrent: checkinBak.protocolrent,
      remark: checkinBak.remark,
      comeinfo: checkinBak.comeinfo,
      goinfo: checkinBak.goinfo,
      phonen: checkinBak.phonen,
      empn2: checkinBak.empn2,
      adhoc: checkinBak.adhoc,
      auditflag: checkinBak.auditflag,
      groupn: checkinBak.groupn,
      payment: checkinBak.payment,
      mtype: checkinBak.mtype,
      memo: checkinBak.memo,
      flight: checkinBak.flight,
      credit: checkinBak.credit,
      talklevel: checkinBak.talklevel,
      lfSign: checkinBak.lfSign,
      keynum: checkinBak.keynum,
      icNum: checkinBak.icNum,
      bh: checkinBak.bh,
      icOwner: checkinBak.icOwner,
      markId: checkinBak.markId,
      gj: checkinBak.gj,
      yfj: checkinBak.yfj,
      hoteldate: checkinBak.hoteldate ? checkinBak.hoteldate.format(DATE_TIME_FORMAT) : null,
      id: checkinBak.id,
    });
  }

  protected createFromForm(): ICheckinBak {
    return {
      ...new CheckinBak(),
      guestId: this.editForm.get(['guestId'])!.value,
      account: this.editForm.get(['account'])!.value,
      hoteltime: this.editForm.get(['hoteltime'])!.value ? dayjs(this.editForm.get(['hoteltime'])!.value, DATE_TIME_FORMAT) : undefined,
      indatetime: this.editForm.get(['indatetime'])!.value ? dayjs(this.editForm.get(['indatetime'])!.value, DATE_TIME_FORMAT) : undefined,
      residefate: this.editForm.get(['residefate'])!.value,
      gotime: this.editForm.get(['gotime'])!.value ? dayjs(this.editForm.get(['gotime'])!.value, DATE_TIME_FORMAT) : undefined,
      empn: this.editForm.get(['empn'])!.value,
      roomn: this.editForm.get(['roomn'])!.value,
      uname: this.editForm.get(['uname'])!.value,
      rentp: this.editForm.get(['rentp'])!.value,
      protocolrent: this.editForm.get(['protocolrent'])!.value,
      remark: this.editForm.get(['remark'])!.value,
      comeinfo: this.editForm.get(['comeinfo'])!.value,
      goinfo: this.editForm.get(['goinfo'])!.value,
      phonen: this.editForm.get(['phonen'])!.value,
      empn2: this.editForm.get(['empn2'])!.value,
      adhoc: this.editForm.get(['adhoc'])!.value,
      auditflag: this.editForm.get(['auditflag'])!.value,
      groupn: this.editForm.get(['groupn'])!.value,
      payment: this.editForm.get(['payment'])!.value,
      mtype: this.editForm.get(['mtype'])!.value,
      memo: this.editForm.get(['memo'])!.value,
      flight: this.editForm.get(['flight'])!.value,
      credit: this.editForm.get(['credit'])!.value,
      talklevel: this.editForm.get(['talklevel'])!.value,
      lfSign: this.editForm.get(['lfSign'])!.value,
      keynum: this.editForm.get(['keynum'])!.value,
      icNum: this.editForm.get(['icNum'])!.value,
      bh: this.editForm.get(['bh'])!.value,
      icOwner: this.editForm.get(['icOwner'])!.value,
      markId: this.editForm.get(['markId'])!.value,
      gj: this.editForm.get(['gj'])!.value,
      yfj: this.editForm.get(['yfj'])!.value,
      hoteldate: this.editForm.get(['hoteldate'])!.value ? dayjs(this.editForm.get(['hoteldate'])!.value, DATE_TIME_FORMAT) : undefined,
      id: this.editForm.get(['id'])!.value,
    };
  }
}
