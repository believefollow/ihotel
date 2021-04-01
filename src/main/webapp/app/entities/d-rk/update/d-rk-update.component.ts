import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDRk, DRk } from '../d-rk.model';
import { DRkService } from '../service/d-rk.service';

@Component({
  selector: 'jhi-d-rk-update',
  templateUrl: './d-rk-update.component.html',
})
export class DRkUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    rkdate: [null, [Validators.required]],
    depot: [null, [Validators.required, Validators.maxLength(20)]],
    rklx: [null, [Validators.maxLength(50)]],
    rkbillno: [null, [Validators.required, Validators.maxLength(20)]],
    company: [],
    deptname: [null, [Validators.maxLength(50)]],
    jbr: [null, [Validators.maxLength(50)]],
    remark: [null, [Validators.maxLength(100)]],
    empn: [null, [Validators.maxLength(50)]],
    lrdate: [],
    spbm: [null, [Validators.required, Validators.maxLength(20)]],
    spmc: [null, [Validators.required, Validators.maxLength(50)]],
    ggxh: [null, [Validators.maxLength(20)]],
    dw: [null, [Validators.maxLength(20)]],
    price: [],
    sl: [],
    je: [],
    sign: [],
    memo: [null, [Validators.maxLength(100)]],
    flag: [],
    f1: [null, [Validators.maxLength(10)]],
    f2: [null, [Validators.maxLength(10)]],
    f1empn: [null, [Validators.maxLength(50)]],
    f2empn: [null, [Validators.maxLength(50)]],
    f1sj: [],
    f2sj: [],
  });

  constructor(protected dRkService: DRkService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dRk }) => {
      if (dRk.id === undefined) {
        const today = dayjs().startOf('day');
        dRk.rkdate = today;
        dRk.lrdate = today;
        dRk.f1sj = today;
        dRk.f2sj = today;
      }

      this.updateForm(dRk);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dRk = this.createFromForm();
    if (dRk.id !== undefined) {
      this.subscribeToSaveResponse(this.dRkService.update(dRk));
    } else {
      this.subscribeToSaveResponse(this.dRkService.create(dRk));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDRk>>): void {
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

  protected updateForm(dRk: IDRk): void {
    this.editForm.patchValue({
      id: dRk.id,
      rkdate: dRk.rkdate ? dRk.rkdate.format(DATE_TIME_FORMAT) : null,
      depot: dRk.depot,
      rklx: dRk.rklx,
      rkbillno: dRk.rkbillno,
      company: dRk.company,
      deptname: dRk.deptname,
      jbr: dRk.jbr,
      remark: dRk.remark,
      empn: dRk.empn,
      lrdate: dRk.lrdate ? dRk.lrdate.format(DATE_TIME_FORMAT) : null,
      spbm: dRk.spbm,
      spmc: dRk.spmc,
      ggxh: dRk.ggxh,
      dw: dRk.dw,
      price: dRk.price,
      sl: dRk.sl,
      je: dRk.je,
      sign: dRk.sign,
      memo: dRk.memo,
      flag: dRk.flag,
      f1: dRk.f1,
      f2: dRk.f2,
      f1empn: dRk.f1empn,
      f2empn: dRk.f2empn,
      f1sj: dRk.f1sj ? dRk.f1sj.format(DATE_TIME_FORMAT) : null,
      f2sj: dRk.f2sj ? dRk.f2sj.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IDRk {
    return {
      ...new DRk(),
      id: this.editForm.get(['id'])!.value,
      rkdate: this.editForm.get(['rkdate'])!.value ? dayjs(this.editForm.get(['rkdate'])!.value, DATE_TIME_FORMAT) : undefined,
      depot: this.editForm.get(['depot'])!.value,
      rklx: this.editForm.get(['rklx'])!.value,
      rkbillno: this.editForm.get(['rkbillno'])!.value,
      company: this.editForm.get(['company'])!.value,
      deptname: this.editForm.get(['deptname'])!.value,
      jbr: this.editForm.get(['jbr'])!.value,
      remark: this.editForm.get(['remark'])!.value,
      empn: this.editForm.get(['empn'])!.value,
      lrdate: this.editForm.get(['lrdate'])!.value ? dayjs(this.editForm.get(['lrdate'])!.value, DATE_TIME_FORMAT) : undefined,
      spbm: this.editForm.get(['spbm'])!.value,
      spmc: this.editForm.get(['spmc'])!.value,
      ggxh: this.editForm.get(['ggxh'])!.value,
      dw: this.editForm.get(['dw'])!.value,
      price: this.editForm.get(['price'])!.value,
      sl: this.editForm.get(['sl'])!.value,
      je: this.editForm.get(['je'])!.value,
      sign: this.editForm.get(['sign'])!.value,
      memo: this.editForm.get(['memo'])!.value,
      flag: this.editForm.get(['flag'])!.value,
      f1: this.editForm.get(['f1'])!.value,
      f2: this.editForm.get(['f2'])!.value,
      f1empn: this.editForm.get(['f1empn'])!.value,
      f2empn: this.editForm.get(['f2empn'])!.value,
      f1sj: this.editForm.get(['f1sj'])!.value ? dayjs(this.editForm.get(['f1sj'])!.value, DATE_TIME_FORMAT) : undefined,
      f2sj: this.editForm.get(['f2sj'])!.value ? dayjs(this.editForm.get(['f2sj'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
