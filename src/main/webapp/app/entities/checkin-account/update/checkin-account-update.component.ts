import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICheckinAccount, CheckinAccount } from '../checkin-account.model';
import { CheckinAccountService } from '../service/checkin-account.service';

@Component({
  selector: 'jhi-checkin-account-update',
  templateUrl: './checkin-account-update.component.html',
})
export class CheckinAccountUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    account: [null, [Validators.required, Validators.maxLength(30)]],
    roomn: [null, [Validators.maxLength(10)]],
    indatetime: [],
    gotime: [],
    kfang: [],
    dhua: [],
    minin: [],
    peich: [],
    qit: [],
    total: [],
  });

  constructor(
    protected checkinAccountService: CheckinAccountService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkinAccount }) => {
      if (checkinAccount.id === undefined) {
        const today = dayjs().startOf('day');
        checkinAccount.indatetime = today;
        checkinAccount.gotime = today;
      }

      this.updateForm(checkinAccount);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkinAccount = this.createFromForm();
    if (checkinAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.checkinAccountService.update(checkinAccount));
    } else {
      this.subscribeToSaveResponse(this.checkinAccountService.create(checkinAccount));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckinAccount>>): void {
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

  protected updateForm(checkinAccount: ICheckinAccount): void {
    this.editForm.patchValue({
      id: checkinAccount.id,
      account: checkinAccount.account,
      roomn: checkinAccount.roomn,
      indatetime: checkinAccount.indatetime ? checkinAccount.indatetime.format(DATE_TIME_FORMAT) : null,
      gotime: checkinAccount.gotime ? checkinAccount.gotime.format(DATE_TIME_FORMAT) : null,
      kfang: checkinAccount.kfang,
      dhua: checkinAccount.dhua,
      minin: checkinAccount.minin,
      peich: checkinAccount.peich,
      qit: checkinAccount.qit,
      total: checkinAccount.total,
    });
  }

  protected createFromForm(): ICheckinAccount {
    return {
      ...new CheckinAccount(),
      id: this.editForm.get(['id'])!.value,
      account: this.editForm.get(['account'])!.value,
      roomn: this.editForm.get(['roomn'])!.value,
      indatetime: this.editForm.get(['indatetime'])!.value ? dayjs(this.editForm.get(['indatetime'])!.value, DATE_TIME_FORMAT) : undefined,
      gotime: this.editForm.get(['gotime'])!.value ? dayjs(this.editForm.get(['gotime'])!.value, DATE_TIME_FORMAT) : undefined,
      kfang: this.editForm.get(['kfang'])!.value,
      dhua: this.editForm.get(['dhua'])!.value,
      minin: this.editForm.get(['minin'])!.value,
      peich: this.editForm.get(['peich'])!.value,
      qit: this.editForm.get(['qit'])!.value,
      total: this.editForm.get(['total'])!.value,
    };
  }
}
