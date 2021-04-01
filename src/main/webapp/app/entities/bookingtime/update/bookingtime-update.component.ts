import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IBookingtime, Bookingtime } from '../bookingtime.model';
import { BookingtimeService } from '../service/bookingtime.service';

@Component({
  selector: 'jhi-bookingtime-update',
  templateUrl: './bookingtime-update.component.html',
})
export class BookingtimeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    bookid: [null, [Validators.maxLength(50)]],
    roomn: [null, [Validators.maxLength(20)]],
    booktime: [],
    rtype: [null, [Validators.maxLength(50)]],
    sl: [],
    remark: [null, [Validators.maxLength(100)]],
    sign: [],
    rzsign: [],
  });

  constructor(protected bookingtimeService: BookingtimeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookingtime }) => {
      if (bookingtime.id === undefined) {
        const today = dayjs().startOf('day');
        bookingtime.booktime = today;
      }

      this.updateForm(bookingtime);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bookingtime = this.createFromForm();
    if (bookingtime.id !== undefined) {
      this.subscribeToSaveResponse(this.bookingtimeService.update(bookingtime));
    } else {
      this.subscribeToSaveResponse(this.bookingtimeService.create(bookingtime));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBookingtime>>): void {
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

  protected updateForm(bookingtime: IBookingtime): void {
    this.editForm.patchValue({
      id: bookingtime.id,
      bookid: bookingtime.bookid,
      roomn: bookingtime.roomn,
      booktime: bookingtime.booktime ? bookingtime.booktime.format(DATE_TIME_FORMAT) : null,
      rtype: bookingtime.rtype,
      sl: bookingtime.sl,
      remark: bookingtime.remark,
      sign: bookingtime.sign,
      rzsign: bookingtime.rzsign,
    });
  }

  protected createFromForm(): IBookingtime {
    return {
      ...new Bookingtime(),
      id: this.editForm.get(['id'])!.value,
      bookid: this.editForm.get(['bookid'])!.value,
      roomn: this.editForm.get(['roomn'])!.value,
      booktime: this.editForm.get(['booktime'])!.value ? dayjs(this.editForm.get(['booktime'])!.value, DATE_TIME_FORMAT) : undefined,
      rtype: this.editForm.get(['rtype'])!.value,
      sl: this.editForm.get(['sl'])!.value,
      remark: this.editForm.get(['remark'])!.value,
      sign: this.editForm.get(['sign'])!.value,
      rzsign: this.editForm.get(['rzsign'])!.value,
    };
  }
}
