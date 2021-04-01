import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICheckin, Checkin } from '../checkin.model';
import { CheckinService } from '../service/checkin.service';

@Component({
  selector: 'jhi-checkin-update',
  templateUrl: './checkin-update.component.html',
})
export class CheckinUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    bkid: [null, [Validators.required]],
    guestId: [null, [Validators.required]],
    account: [null, [Validators.required, Validators.maxLength(30)]],
    hoteltime: [],
    indatetime: [],
    residefate: [],
    gotime: [],
    empn: [null, [Validators.maxLength(10)]],
    roomn: [null, [Validators.required, Validators.maxLength(10)]],
    uname: [null, [Validators.maxLength(50)]],
    rentp: [null, [Validators.required, Validators.maxLength(10)]],
    protocolrent: [],
    remark: [null, [Validators.maxLength(8000)]],
    phonen: [null, [Validators.maxLength(10)]],
    empn2: [null, [Validators.maxLength(10)]],
    adhoc: [null, [Validators.maxLength(40)]],
    auditflag: [],
    groupn: [null, [Validators.maxLength(20)]],
    memo: [null, [Validators.maxLength(500)]],
    lfSign: [null, [Validators.maxLength(1)]],
    keynum: [null, [Validators.maxLength(3)]],
    hykh: [null, [Validators.maxLength(50)]],
    bm: [null, [Validators.maxLength(10)]],
    flag: [],
    jxtime: [],
    jxflag: [],
    checkf: [],
    guestname: [null, [Validators.required, Validators.maxLength(45)]],
    fgf: [null, [Validators.required]],
    fgxx: [null, [Validators.maxLength(200)]],
    hourSign: [],
    xsy: [null, [Validators.maxLength(20)]],
    rzsign: [],
    jf: [],
    gname: [null, [Validators.maxLength(50)]],
    zcsign: [],
    cqsl: [],
    sfjf: [],
    ywly: [null, [Validators.maxLength(20)]],
    fk: [null, [Validators.maxLength(2)]],
    fkrq: [],
    bc: [null, [Validators.maxLength(50)]],
    jxremark: [null, [Validators.maxLength(100)]],
    txid: [],
    cfr: [null, [Validators.maxLength(50)]],
    fjbm: [null, [Validators.maxLength(2)]],
    djlx: [null, [Validators.maxLength(100)]],
    wlddh: [null, [Validators.maxLength(100)]],
    fksl: [],
    dqtx: [null, [Validators.maxLength(1)]],
  });

  constructor(protected checkinService: CheckinService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkin }) => {
      if (checkin.id === undefined) {
        const today = dayjs().startOf('day');
        checkin.hoteltime = today;
        checkin.indatetime = today;
        checkin.gotime = today;
        checkin.jxtime = today;
        checkin.fkrq = today;
      }

      this.updateForm(checkin);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkin = this.createFromForm();
    if (checkin.id !== undefined) {
      this.subscribeToSaveResponse(this.checkinService.update(checkin));
    } else {
      this.subscribeToSaveResponse(this.checkinService.create(checkin));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckin>>): void {
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

  protected updateForm(checkin: ICheckin): void {
    this.editForm.patchValue({
      id: checkin.id,
      bkid: checkin.bkid,
      guestId: checkin.guestId,
      account: checkin.account,
      hoteltime: checkin.hoteltime ? checkin.hoteltime.format(DATE_TIME_FORMAT) : null,
      indatetime: checkin.indatetime ? checkin.indatetime.format(DATE_TIME_FORMAT) : null,
      residefate: checkin.residefate,
      gotime: checkin.gotime ? checkin.gotime.format(DATE_TIME_FORMAT) : null,
      empn: checkin.empn,
      roomn: checkin.roomn,
      uname: checkin.uname,
      rentp: checkin.rentp,
      protocolrent: checkin.protocolrent,
      remark: checkin.remark,
      phonen: checkin.phonen,
      empn2: checkin.empn2,
      adhoc: checkin.adhoc,
      auditflag: checkin.auditflag,
      groupn: checkin.groupn,
      memo: checkin.memo,
      lfSign: checkin.lfSign,
      keynum: checkin.keynum,
      hykh: checkin.hykh,
      bm: checkin.bm,
      flag: checkin.flag,
      jxtime: checkin.jxtime ? checkin.jxtime.format(DATE_TIME_FORMAT) : null,
      jxflag: checkin.jxflag,
      checkf: checkin.checkf,
      guestname: checkin.guestname,
      fgf: checkin.fgf,
      fgxx: checkin.fgxx,
      hourSign: checkin.hourSign,
      xsy: checkin.xsy,
      rzsign: checkin.rzsign,
      jf: checkin.jf,
      gname: checkin.gname,
      zcsign: checkin.zcsign,
      cqsl: checkin.cqsl,
      sfjf: checkin.sfjf,
      ywly: checkin.ywly,
      fk: checkin.fk,
      fkrq: checkin.fkrq ? checkin.fkrq.format(DATE_TIME_FORMAT) : null,
      bc: checkin.bc,
      jxremark: checkin.jxremark,
      txid: checkin.txid,
      cfr: checkin.cfr,
      fjbm: checkin.fjbm,
      djlx: checkin.djlx,
      wlddh: checkin.wlddh,
      fksl: checkin.fksl,
      dqtx: checkin.dqtx,
    });
  }

  protected createFromForm(): ICheckin {
    return {
      ...new Checkin(),
      id: this.editForm.get(['id'])!.value,
      bkid: this.editForm.get(['bkid'])!.value,
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
      phonen: this.editForm.get(['phonen'])!.value,
      empn2: this.editForm.get(['empn2'])!.value,
      adhoc: this.editForm.get(['adhoc'])!.value,
      auditflag: this.editForm.get(['auditflag'])!.value,
      groupn: this.editForm.get(['groupn'])!.value,
      memo: this.editForm.get(['memo'])!.value,
      lfSign: this.editForm.get(['lfSign'])!.value,
      keynum: this.editForm.get(['keynum'])!.value,
      hykh: this.editForm.get(['hykh'])!.value,
      bm: this.editForm.get(['bm'])!.value,
      flag: this.editForm.get(['flag'])!.value,
      jxtime: this.editForm.get(['jxtime'])!.value ? dayjs(this.editForm.get(['jxtime'])!.value, DATE_TIME_FORMAT) : undefined,
      jxflag: this.editForm.get(['jxflag'])!.value,
      checkf: this.editForm.get(['checkf'])!.value,
      guestname: this.editForm.get(['guestname'])!.value,
      fgf: this.editForm.get(['fgf'])!.value,
      fgxx: this.editForm.get(['fgxx'])!.value,
      hourSign: this.editForm.get(['hourSign'])!.value,
      xsy: this.editForm.get(['xsy'])!.value,
      rzsign: this.editForm.get(['rzsign'])!.value,
      jf: this.editForm.get(['jf'])!.value,
      gname: this.editForm.get(['gname'])!.value,
      zcsign: this.editForm.get(['zcsign'])!.value,
      cqsl: this.editForm.get(['cqsl'])!.value,
      sfjf: this.editForm.get(['sfjf'])!.value,
      ywly: this.editForm.get(['ywly'])!.value,
      fk: this.editForm.get(['fk'])!.value,
      fkrq: this.editForm.get(['fkrq'])!.value ? dayjs(this.editForm.get(['fkrq'])!.value, DATE_TIME_FORMAT) : undefined,
      bc: this.editForm.get(['bc'])!.value,
      jxremark: this.editForm.get(['jxremark'])!.value,
      txid: this.editForm.get(['txid'])!.value,
      cfr: this.editForm.get(['cfr'])!.value,
      fjbm: this.editForm.get(['fjbm'])!.value,
      djlx: this.editForm.get(['djlx'])!.value,
      wlddh: this.editForm.get(['wlddh'])!.value,
      fksl: this.editForm.get(['fksl'])!.value,
      dqtx: this.editForm.get(['dqtx'])!.value,
    };
  }
}
