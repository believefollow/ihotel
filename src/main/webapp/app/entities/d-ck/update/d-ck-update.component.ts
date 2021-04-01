import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDCk, DCk } from '../d-ck.model';
import { DCkService } from '../service/d-ck.service';

@Component({
  selector: 'jhi-d-ck-update',
  templateUrl: './d-ck-update.component.html',
})
export class DCkUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    depot: [null, [Validators.required, Validators.maxLength(20)]],
    ckdate: [null, [Validators.required]],
    ckbillno: [null, [Validators.required, Validators.maxLength(20)]],
    deptname: [null, [Validators.maxLength(20)]],
    jbr: [null, [Validators.maxLength(20)]],
    remark: [null, [Validators.maxLength(100)]],
    spbm: [null, [Validators.required, Validators.maxLength(20)]],
    spmc: [null, [Validators.required, Validators.maxLength(50)]],
    unit: [null, [Validators.maxLength(20)]],
    price: [],
    sl: [],
    je: [],
    memo: [null, [Validators.maxLength(100)]],
    flag: [],
    dbSign: [],
    cktype: [null, [Validators.maxLength(30)]],
    empn: [null, [Validators.maxLength(20)]],
    lrdate: [],
    kcid: [],
    f1: [null, [Validators.maxLength(10)]],
    f2: [null, [Validators.maxLength(10)]],
    f1empn: [null, [Validators.maxLength(50)]],
    f2empn: [null, [Validators.maxLength(50)]],
    f1sj: [],
    f2sj: [],
  });

  constructor(protected dCkService: DCkService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dCk }) => {
      if (dCk.id === undefined) {
        const today = dayjs().startOf('day');
        dCk.ckdate = today;
        dCk.lrdate = today;
        dCk.f1sj = today;
        dCk.f2sj = today;
      }

      this.updateForm(dCk);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dCk = this.createFromForm();
    if (dCk.id !== undefined) {
      this.subscribeToSaveResponse(this.dCkService.update(dCk));
    } else {
      this.subscribeToSaveResponse(this.dCkService.create(dCk));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDCk>>): void {
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

  protected updateForm(dCk: IDCk): void {
    this.editForm.patchValue({
      id: dCk.id,
      depot: dCk.depot,
      ckdate: dCk.ckdate ? dCk.ckdate.format(DATE_TIME_FORMAT) : null,
      ckbillno: dCk.ckbillno,
      deptname: dCk.deptname,
      jbr: dCk.jbr,
      remark: dCk.remark,
      spbm: dCk.spbm,
      spmc: dCk.spmc,
      unit: dCk.unit,
      price: dCk.price,
      sl: dCk.sl,
      je: dCk.je,
      memo: dCk.memo,
      flag: dCk.flag,
      dbSign: dCk.dbSign,
      cktype: dCk.cktype,
      empn: dCk.empn,
      lrdate: dCk.lrdate ? dCk.lrdate.format(DATE_TIME_FORMAT) : null,
      kcid: dCk.kcid,
      f1: dCk.f1,
      f2: dCk.f2,
      f1empn: dCk.f1empn,
      f2empn: dCk.f2empn,
      f1sj: dCk.f1sj ? dCk.f1sj.format(DATE_TIME_FORMAT) : null,
      f2sj: dCk.f2sj ? dCk.f2sj.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IDCk {
    return {
      ...new DCk(),
      id: this.editForm.get(['id'])!.value,
      depot: this.editForm.get(['depot'])!.value,
      ckdate: this.editForm.get(['ckdate'])!.value ? dayjs(this.editForm.get(['ckdate'])!.value, DATE_TIME_FORMAT) : undefined,
      ckbillno: this.editForm.get(['ckbillno'])!.value,
      deptname: this.editForm.get(['deptname'])!.value,
      jbr: this.editForm.get(['jbr'])!.value,
      remark: this.editForm.get(['remark'])!.value,
      spbm: this.editForm.get(['spbm'])!.value,
      spmc: this.editForm.get(['spmc'])!.value,
      unit: this.editForm.get(['unit'])!.value,
      price: this.editForm.get(['price'])!.value,
      sl: this.editForm.get(['sl'])!.value,
      je: this.editForm.get(['je'])!.value,
      memo: this.editForm.get(['memo'])!.value,
      flag: this.editForm.get(['flag'])!.value,
      dbSign: this.editForm.get(['dbSign'])!.value,
      cktype: this.editForm.get(['cktype'])!.value,
      empn: this.editForm.get(['empn'])!.value,
      lrdate: this.editForm.get(['lrdate'])!.value ? dayjs(this.editForm.get(['lrdate'])!.value, DATE_TIME_FORMAT) : undefined,
      kcid: this.editForm.get(['kcid'])!.value,
      f1: this.editForm.get(['f1'])!.value,
      f2: this.editForm.get(['f2'])!.value,
      f1empn: this.editForm.get(['f1empn'])!.value,
      f2empn: this.editForm.get(['f2empn'])!.value,
      f1sj: this.editForm.get(['f1sj'])!.value ? dayjs(this.editForm.get(['f1sj'])!.value, DATE_TIME_FORMAT) : undefined,
      f2sj: this.editForm.get(['f2sj'])!.value ? dayjs(this.editForm.get(['f2sj'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
