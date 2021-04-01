import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IAccounts, Accounts } from '../accounts.model';
import { AccountsService } from '../service/accounts.service';

@Component({
  selector: 'jhi-accounts-update',
  templateUrl: './accounts-update.component.html',
})
export class AccountsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    account: [null, [Validators.required, Validators.maxLength(30)]],
    consumetime: [null, [Validators.required]],
    hoteltime: [],
    feenum: [],
    money: [],
    memo: [null, [Validators.maxLength(100)]],
    empn: [null, [Validators.maxLength(10)]],
    imprest: [],
    propertiy: [null, [Validators.maxLength(100)]],
    earntypen: [],
    payment: [],
    roomn: [null, [Validators.maxLength(10)]],
    id: [null, [Validators.required]],
    ulogogram: [null, [Validators.maxLength(30)]],
    lk: [],
    acc: [null, [Validators.maxLength(50)]],
    jzSign: [],
    jzflag: [],
    sign: [null, [Validators.maxLength(4)]],
    bs: [],
    jzhotel: [],
    jzempn: [null, [Validators.maxLength(50)]],
    jztime: [],
    chonghong: [],
    billno: [null, [Validators.maxLength(10)]],
    printcount: [],
    vipjf: [],
    hykh: [null, [Validators.maxLength(20)]],
    sl: [],
    sgdjh: [null, [Validators.maxLength(20)]],
    hoteldm: [null, [Validators.maxLength(20)]],
    isnew: [],
    guestId: [],
    yhkh: [null, [Validators.maxLength(50)]],
    djq: [null, [Validators.maxLength(100)]],
    ysje: [],
    bj: [null, [Validators.maxLength(2)]],
    bjempn: [null, [Validators.maxLength(50)]],
    bjtime: [],
    paper2: [null, [Validators.maxLength(50)]],
    bc: [null, [Validators.maxLength(20)]],
    auto: [null, [Validators.maxLength(2)]],
    xsy: [null, [Validators.maxLength(50)]],
    djkh: [null, [Validators.maxLength(50)]],
    djsign: [null, [Validators.maxLength(2)]],
    classname: [null, [Validators.maxLength(50)]],
    iscy: [null, [Validators.maxLength(20)]],
    bsign: [null, [Validators.maxLength(2)]],
    fx: [null, [Validators.maxLength(2)]],
    djlx: [null, [Validators.maxLength(50)]],
    isup: [],
    yongjin: [],
    czpc: [null, [Validators.maxLength(50)]],
    cxflag: [],
    pmemo: [null, [Validators.maxLength(200)]],
    czbillno: [null, [Validators.maxLength(50)]],
    djqbz: [null, [Validators.maxLength(50)]],
    ysqmemo: [null, [Validators.maxLength(100)]],
    transactionId: [null, [Validators.maxLength(100)]],
    outTradeNo: [null, [Validators.maxLength(100)]],
    gsname: [null, [Validators.maxLength(200)]],
    rz: [],
    gz: [],
    ts: [],
    ky: [null, [Validators.maxLength(50)]],
    xy: [null, [Validators.maxLength(50)]],
    roomtype: [null, [Validators.maxLength(50)]],
    bkid: [],
  });

  constructor(protected accountsService: AccountsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accounts }) => {
      if (accounts.id === undefined) {
        const today = dayjs().startOf('day');
        accounts.consumetime = today;
        accounts.hoteltime = today;
        accounts.jzhotel = today;
        accounts.jztime = today;
        accounts.bjtime = today;
        accounts.rz = today;
        accounts.gz = today;
      }

      this.updateForm(accounts);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accounts = this.createFromForm();
    if (accounts.id !== undefined) {
      this.subscribeToSaveResponse(this.accountsService.update(accounts));
    } else {
      this.subscribeToSaveResponse(this.accountsService.create(accounts));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccounts>>): void {
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

  protected updateForm(accounts: IAccounts): void {
    this.editForm.patchValue({
      account: accounts.account,
      consumetime: accounts.consumetime ? accounts.consumetime.format(DATE_TIME_FORMAT) : null,
      hoteltime: accounts.hoteltime ? accounts.hoteltime.format(DATE_TIME_FORMAT) : null,
      feenum: accounts.feenum,
      money: accounts.money,
      memo: accounts.memo,
      empn: accounts.empn,
      imprest: accounts.imprest,
      propertiy: accounts.propertiy,
      earntypen: accounts.earntypen,
      payment: accounts.payment,
      roomn: accounts.roomn,
      id: accounts.id,
      ulogogram: accounts.ulogogram,
      lk: accounts.lk,
      acc: accounts.acc,
      jzSign: accounts.jzSign,
      jzflag: accounts.jzflag,
      sign: accounts.sign,
      bs: accounts.bs,
      jzhotel: accounts.jzhotel ? accounts.jzhotel.format(DATE_TIME_FORMAT) : null,
      jzempn: accounts.jzempn,
      jztime: accounts.jztime ? accounts.jztime.format(DATE_TIME_FORMAT) : null,
      chonghong: accounts.chonghong,
      billno: accounts.billno,
      printcount: accounts.printcount,
      vipjf: accounts.vipjf,
      hykh: accounts.hykh,
      sl: accounts.sl,
      sgdjh: accounts.sgdjh,
      hoteldm: accounts.hoteldm,
      isnew: accounts.isnew,
      guestId: accounts.guestId,
      yhkh: accounts.yhkh,
      djq: accounts.djq,
      ysje: accounts.ysje,
      bj: accounts.bj,
      bjempn: accounts.bjempn,
      bjtime: accounts.bjtime ? accounts.bjtime.format(DATE_TIME_FORMAT) : null,
      paper2: accounts.paper2,
      bc: accounts.bc,
      auto: accounts.auto,
      xsy: accounts.xsy,
      djkh: accounts.djkh,
      djsign: accounts.djsign,
      classname: accounts.classname,
      iscy: accounts.iscy,
      bsign: accounts.bsign,
      fx: accounts.fx,
      djlx: accounts.djlx,
      isup: accounts.isup,
      yongjin: accounts.yongjin,
      czpc: accounts.czpc,
      cxflag: accounts.cxflag,
      pmemo: accounts.pmemo,
      czbillno: accounts.czbillno,
      djqbz: accounts.djqbz,
      ysqmemo: accounts.ysqmemo,
      transactionId: accounts.transactionId,
      outTradeNo: accounts.outTradeNo,
      gsname: accounts.gsname,
      rz: accounts.rz ? accounts.rz.format(DATE_TIME_FORMAT) : null,
      gz: accounts.gz ? accounts.gz.format(DATE_TIME_FORMAT) : null,
      ts: accounts.ts,
      ky: accounts.ky,
      xy: accounts.xy,
      roomtype: accounts.roomtype,
      bkid: accounts.bkid,
    });
  }

  protected createFromForm(): IAccounts {
    return {
      ...new Accounts(),
      account: this.editForm.get(['account'])!.value,
      consumetime: this.editForm.get(['consumetime'])!.value
        ? dayjs(this.editForm.get(['consumetime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      hoteltime: this.editForm.get(['hoteltime'])!.value ? dayjs(this.editForm.get(['hoteltime'])!.value, DATE_TIME_FORMAT) : undefined,
      feenum: this.editForm.get(['feenum'])!.value,
      money: this.editForm.get(['money'])!.value,
      memo: this.editForm.get(['memo'])!.value,
      empn: this.editForm.get(['empn'])!.value,
      imprest: this.editForm.get(['imprest'])!.value,
      propertiy: this.editForm.get(['propertiy'])!.value,
      earntypen: this.editForm.get(['earntypen'])!.value,
      payment: this.editForm.get(['payment'])!.value,
      roomn: this.editForm.get(['roomn'])!.value,
      id: this.editForm.get(['id'])!.value,
      ulogogram: this.editForm.get(['ulogogram'])!.value,
      lk: this.editForm.get(['lk'])!.value,
      acc: this.editForm.get(['acc'])!.value,
      jzSign: this.editForm.get(['jzSign'])!.value,
      jzflag: this.editForm.get(['jzflag'])!.value,
      sign: this.editForm.get(['sign'])!.value,
      bs: this.editForm.get(['bs'])!.value,
      jzhotel: this.editForm.get(['jzhotel'])!.value ? dayjs(this.editForm.get(['jzhotel'])!.value, DATE_TIME_FORMAT) : undefined,
      jzempn: this.editForm.get(['jzempn'])!.value,
      jztime: this.editForm.get(['jztime'])!.value ? dayjs(this.editForm.get(['jztime'])!.value, DATE_TIME_FORMAT) : undefined,
      chonghong: this.editForm.get(['chonghong'])!.value,
      billno: this.editForm.get(['billno'])!.value,
      printcount: this.editForm.get(['printcount'])!.value,
      vipjf: this.editForm.get(['vipjf'])!.value,
      hykh: this.editForm.get(['hykh'])!.value,
      sl: this.editForm.get(['sl'])!.value,
      sgdjh: this.editForm.get(['sgdjh'])!.value,
      hoteldm: this.editForm.get(['hoteldm'])!.value,
      isnew: this.editForm.get(['isnew'])!.value,
      guestId: this.editForm.get(['guestId'])!.value,
      yhkh: this.editForm.get(['yhkh'])!.value,
      djq: this.editForm.get(['djq'])!.value,
      ysje: this.editForm.get(['ysje'])!.value,
      bj: this.editForm.get(['bj'])!.value,
      bjempn: this.editForm.get(['bjempn'])!.value,
      bjtime: this.editForm.get(['bjtime'])!.value ? dayjs(this.editForm.get(['bjtime'])!.value, DATE_TIME_FORMAT) : undefined,
      paper2: this.editForm.get(['paper2'])!.value,
      bc: this.editForm.get(['bc'])!.value,
      auto: this.editForm.get(['auto'])!.value,
      xsy: this.editForm.get(['xsy'])!.value,
      djkh: this.editForm.get(['djkh'])!.value,
      djsign: this.editForm.get(['djsign'])!.value,
      classname: this.editForm.get(['classname'])!.value,
      iscy: this.editForm.get(['iscy'])!.value,
      bsign: this.editForm.get(['bsign'])!.value,
      fx: this.editForm.get(['fx'])!.value,
      djlx: this.editForm.get(['djlx'])!.value,
      isup: this.editForm.get(['isup'])!.value,
      yongjin: this.editForm.get(['yongjin'])!.value,
      czpc: this.editForm.get(['czpc'])!.value,
      cxflag: this.editForm.get(['cxflag'])!.value,
      pmemo: this.editForm.get(['pmemo'])!.value,
      czbillno: this.editForm.get(['czbillno'])!.value,
      djqbz: this.editForm.get(['djqbz'])!.value,
      ysqmemo: this.editForm.get(['ysqmemo'])!.value,
      transactionId: this.editForm.get(['transactionId'])!.value,
      outTradeNo: this.editForm.get(['outTradeNo'])!.value,
      gsname: this.editForm.get(['gsname'])!.value,
      rz: this.editForm.get(['rz'])!.value ? dayjs(this.editForm.get(['rz'])!.value, DATE_TIME_FORMAT) : undefined,
      gz: this.editForm.get(['gz'])!.value ? dayjs(this.editForm.get(['gz'])!.value, DATE_TIME_FORMAT) : undefined,
      ts: this.editForm.get(['ts'])!.value,
      ky: this.editForm.get(['ky'])!.value,
      xy: this.editForm.get(['xy'])!.value,
      roomtype: this.editForm.get(['roomtype'])!.value,
      bkid: this.editForm.get(['bkid'])!.value,
    };
  }
}
