import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICzlCz, CzlCz } from '../czl-cz.model';
import { CzlCzService } from '../service/czl-cz.service';

@Component({
  selector: 'jhi-czl-cz-update',
  templateUrl: './czl-cz-update.component.html',
})
export class CzlCzUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tjrq: [null, [Validators.required]],
    typeid: [],
    type: [null, [Validators.required, Validators.maxLength(50)]],
    fjsl: [],
    kfl: [],
    pjz: [],
    ysfz: [],
    sjfz: [],
    fzcz: [],
    pjzcj: [],
    kfsM: [],
    kflM: [],
    pjzM: [],
    fzsr: [],
    dayz: [],
    hoteltime: [],
    empn: [null, [Validators.maxLength(45)]],
    monthz: [],
    hoteldm: [null, [Validators.maxLength(50)]],
    isnew: [],
  });

  constructor(protected czlCzService: CzlCzService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ czlCz }) => {
      if (czlCz.id === undefined) {
        const today = dayjs().startOf('day');
        czlCz.tjrq = today;
        czlCz.hoteltime = today;
      }

      this.updateForm(czlCz);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const czlCz = this.createFromForm();
    if (czlCz.id !== undefined) {
      this.subscribeToSaveResponse(this.czlCzService.update(czlCz));
    } else {
      this.subscribeToSaveResponse(this.czlCzService.create(czlCz));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICzlCz>>): void {
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

  protected updateForm(czlCz: ICzlCz): void {
    this.editForm.patchValue({
      id: czlCz.id,
      tjrq: czlCz.tjrq ? czlCz.tjrq.format(DATE_TIME_FORMAT) : null,
      typeid: czlCz.typeid,
      type: czlCz.type,
      fjsl: czlCz.fjsl,
      kfl: czlCz.kfl,
      pjz: czlCz.pjz,
      ysfz: czlCz.ysfz,
      sjfz: czlCz.sjfz,
      fzcz: czlCz.fzcz,
      pjzcj: czlCz.pjzcj,
      kfsM: czlCz.kfsM,
      kflM: czlCz.kflM,
      pjzM: czlCz.pjzM,
      fzsr: czlCz.fzsr,
      dayz: czlCz.dayz,
      hoteltime: czlCz.hoteltime ? czlCz.hoteltime.format(DATE_TIME_FORMAT) : null,
      empn: czlCz.empn,
      monthz: czlCz.monthz,
      hoteldm: czlCz.hoteldm,
      isnew: czlCz.isnew,
    });
  }

  protected createFromForm(): ICzlCz {
    return {
      ...new CzlCz(),
      id: this.editForm.get(['id'])!.value,
      tjrq: this.editForm.get(['tjrq'])!.value ? dayjs(this.editForm.get(['tjrq'])!.value, DATE_TIME_FORMAT) : undefined,
      typeid: this.editForm.get(['typeid'])!.value,
      type: this.editForm.get(['type'])!.value,
      fjsl: this.editForm.get(['fjsl'])!.value,
      kfl: this.editForm.get(['kfl'])!.value,
      pjz: this.editForm.get(['pjz'])!.value,
      ysfz: this.editForm.get(['ysfz'])!.value,
      sjfz: this.editForm.get(['sjfz'])!.value,
      fzcz: this.editForm.get(['fzcz'])!.value,
      pjzcj: this.editForm.get(['pjzcj'])!.value,
      kfsM: this.editForm.get(['kfsM'])!.value,
      kflM: this.editForm.get(['kflM'])!.value,
      pjzM: this.editForm.get(['pjzM'])!.value,
      fzsr: this.editForm.get(['fzsr'])!.value,
      dayz: this.editForm.get(['dayz'])!.value,
      hoteltime: this.editForm.get(['hoteltime'])!.value ? dayjs(this.editForm.get(['hoteltime'])!.value, DATE_TIME_FORMAT) : undefined,
      empn: this.editForm.get(['empn'])!.value,
      monthz: this.editForm.get(['monthz'])!.value,
      hoteldm: this.editForm.get(['hoteldm'])!.value,
      isnew: this.editForm.get(['isnew'])!.value,
    };
  }
}
