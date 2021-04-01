import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICardysq, Cardysq } from '../cardysq.model';
import { CardysqService } from '../service/cardysq.service';

@Component({
  selector: 'jhi-cardysq-update',
  templateUrl: './cardysq-update.component.html',
})
export class CardysqUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    roomn: [null, [Validators.maxLength(30)]],
    guestname: [null, [Validators.maxLength(50)]],
    account: [null, [Validators.maxLength(50)]],
    rq: [],
    cardid: [null, [Validators.maxLength(100)]],
    djh: [null, [Validators.maxLength(100)]],
    sqh: [null, [Validators.maxLength(100)]],
    empn: [null, [Validators.maxLength(50)]],
    sign: [null, [Validators.maxLength(2)]],
    hoteltime: [],
    yxrq: [],
    je: [],
    ysqmemo: [null, [Validators.maxLength(100)]],
  });

  constructor(protected cardysqService: CardysqService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cardysq }) => {
      if (cardysq.id === undefined) {
        const today = dayjs().startOf('day');
        cardysq.rq = today;
        cardysq.hoteltime = today;
        cardysq.yxrq = today;
      }

      this.updateForm(cardysq);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cardysq = this.createFromForm();
    if (cardysq.id !== undefined) {
      this.subscribeToSaveResponse(this.cardysqService.update(cardysq));
    } else {
      this.subscribeToSaveResponse(this.cardysqService.create(cardysq));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICardysq>>): void {
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

  protected updateForm(cardysq: ICardysq): void {
    this.editForm.patchValue({
      id: cardysq.id,
      roomn: cardysq.roomn,
      guestname: cardysq.guestname,
      account: cardysq.account,
      rq: cardysq.rq ? cardysq.rq.format(DATE_TIME_FORMAT) : null,
      cardid: cardysq.cardid,
      djh: cardysq.djh,
      sqh: cardysq.sqh,
      empn: cardysq.empn,
      sign: cardysq.sign,
      hoteltime: cardysq.hoteltime ? cardysq.hoteltime.format(DATE_TIME_FORMAT) : null,
      yxrq: cardysq.yxrq ? cardysq.yxrq.format(DATE_TIME_FORMAT) : null,
      je: cardysq.je,
      ysqmemo: cardysq.ysqmemo,
    });
  }

  protected createFromForm(): ICardysq {
    return {
      ...new Cardysq(),
      id: this.editForm.get(['id'])!.value,
      roomn: this.editForm.get(['roomn'])!.value,
      guestname: this.editForm.get(['guestname'])!.value,
      account: this.editForm.get(['account'])!.value,
      rq: this.editForm.get(['rq'])!.value ? dayjs(this.editForm.get(['rq'])!.value, DATE_TIME_FORMAT) : undefined,
      cardid: this.editForm.get(['cardid'])!.value,
      djh: this.editForm.get(['djh'])!.value,
      sqh: this.editForm.get(['sqh'])!.value,
      empn: this.editForm.get(['empn'])!.value,
      sign: this.editForm.get(['sign'])!.value,
      hoteltime: this.editForm.get(['hoteltime'])!.value ? dayjs(this.editForm.get(['hoteltime'])!.value, DATE_TIME_FORMAT) : undefined,
      yxrq: this.editForm.get(['yxrq'])!.value ? dayjs(this.editForm.get(['yxrq'])!.value, DATE_TIME_FORMAT) : undefined,
      je: this.editForm.get(['je'])!.value,
      ysqmemo: this.editForm.get(['ysqmemo'])!.value,
    };
  }
}
