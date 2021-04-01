import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICzCzl2, CzCzl2 } from '../cz-czl-2.model';
import { CzCzl2Service } from '../service/cz-czl-2.service';

@Component({
  selector: 'jhi-cz-czl-2-update',
  templateUrl: './cz-czl-2-update.component.html',
})
export class CzCzl2UpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    dr: [],
    type: [null, [Validators.maxLength(50)]],
    fs: [],
    kfl: [],
    fzsr: [],
    pjz: [],
    fsM: [],
    kflM: [],
    fzsrM: [],
    pjzM: [],
    fsY: [],
    kflY: [],
    fzsrY: [],
    pjzY: [],
    fsQ: [],
    kflQ: [],
    fzsrQ: [],
    pjzQ: [],
    dateY: [null, [Validators.maxLength(50)]],
    dqdate: [],
    empn: [null, [Validators.maxLength(45)]],
    number: [null, [Validators.required]],
    numberM: [null, [Validators.required]],
    numberY: [null, [Validators.required]],
    hoteldm: [null, [Validators.maxLength(50)]],
    isnew: [],
  });

  constructor(protected czCzl2Service: CzCzl2Service, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ czCzl2 }) => {
      if (czCzl2.id === undefined) {
        const today = dayjs().startOf('day');
        czCzl2.dr = today;
        czCzl2.dqdate = today;
      }

      this.updateForm(czCzl2);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const czCzl2 = this.createFromForm();
    if (czCzl2.id !== undefined) {
      this.subscribeToSaveResponse(this.czCzl2Service.update(czCzl2));
    } else {
      this.subscribeToSaveResponse(this.czCzl2Service.create(czCzl2));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICzCzl2>>): void {
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

  protected updateForm(czCzl2: ICzCzl2): void {
    this.editForm.patchValue({
      id: czCzl2.id,
      dr: czCzl2.dr ? czCzl2.dr.format(DATE_TIME_FORMAT) : null,
      type: czCzl2.type,
      fs: czCzl2.fs,
      kfl: czCzl2.kfl,
      fzsr: czCzl2.fzsr,
      pjz: czCzl2.pjz,
      fsM: czCzl2.fsM,
      kflM: czCzl2.kflM,
      fzsrM: czCzl2.fzsrM,
      pjzM: czCzl2.pjzM,
      fsY: czCzl2.fsY,
      kflY: czCzl2.kflY,
      fzsrY: czCzl2.fzsrY,
      pjzY: czCzl2.pjzY,
      fsQ: czCzl2.fsQ,
      kflQ: czCzl2.kflQ,
      fzsrQ: czCzl2.fzsrQ,
      pjzQ: czCzl2.pjzQ,
      dateY: czCzl2.dateY,
      dqdate: czCzl2.dqdate ? czCzl2.dqdate.format(DATE_TIME_FORMAT) : null,
      empn: czCzl2.empn,
      number: czCzl2.number,
      numberM: czCzl2.numberM,
      numberY: czCzl2.numberY,
      hoteldm: czCzl2.hoteldm,
      isnew: czCzl2.isnew,
    });
  }

  protected createFromForm(): ICzCzl2 {
    return {
      ...new CzCzl2(),
      id: this.editForm.get(['id'])!.value,
      dr: this.editForm.get(['dr'])!.value ? dayjs(this.editForm.get(['dr'])!.value, DATE_TIME_FORMAT) : undefined,
      type: this.editForm.get(['type'])!.value,
      fs: this.editForm.get(['fs'])!.value,
      kfl: this.editForm.get(['kfl'])!.value,
      fzsr: this.editForm.get(['fzsr'])!.value,
      pjz: this.editForm.get(['pjz'])!.value,
      fsM: this.editForm.get(['fsM'])!.value,
      kflM: this.editForm.get(['kflM'])!.value,
      fzsrM: this.editForm.get(['fzsrM'])!.value,
      pjzM: this.editForm.get(['pjzM'])!.value,
      fsY: this.editForm.get(['fsY'])!.value,
      kflY: this.editForm.get(['kflY'])!.value,
      fzsrY: this.editForm.get(['fzsrY'])!.value,
      pjzY: this.editForm.get(['pjzY'])!.value,
      fsQ: this.editForm.get(['fsQ'])!.value,
      kflQ: this.editForm.get(['kflQ'])!.value,
      fzsrQ: this.editForm.get(['fzsrQ'])!.value,
      pjzQ: this.editForm.get(['pjzQ'])!.value,
      dateY: this.editForm.get(['dateY'])!.value,
      dqdate: this.editForm.get(['dqdate'])!.value ? dayjs(this.editForm.get(['dqdate'])!.value, DATE_TIME_FORMAT) : undefined,
      empn: this.editForm.get(['empn'])!.value,
      number: this.editForm.get(['number'])!.value,
      numberM: this.editForm.get(['numberM'])!.value,
      numberY: this.editForm.get(['numberY'])!.value,
      hoteldm: this.editForm.get(['hoteldm'])!.value,
      isnew: this.editForm.get(['isnew'])!.value,
    };
  }
}
