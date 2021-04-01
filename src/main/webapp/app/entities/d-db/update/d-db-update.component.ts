import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDDb, DDb } from '../d-db.model';
import { DDbService } from '../service/d-db.service';

@Component({
  selector: 'jhi-d-db-update',
  templateUrl: './d-db-update.component.html',
})
export class DDbUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    dbdate: [null, [Validators.required]],
    dbbillno: [null, [Validators.required, Validators.maxLength(20)]],
    rdepot: [null, [Validators.required, Validators.maxLength(20)]],
    cdepot: [null, [Validators.required, Validators.maxLength(20)]],
    jbr: [null, [Validators.maxLength(50)]],
    remark: [null, [Validators.maxLength(50)]],
    spbm: [null, [Validators.required, Validators.maxLength(20)]],
    spmc: [null, [Validators.required, Validators.maxLength(20)]],
    unit: [null, [Validators.maxLength(20)]],
    price: [],
    sl: [],
    je: [],
    memo: [null, [Validators.maxLength(100)]],
    flag: [],
    kcid: [],
    empn: [null, [Validators.maxLength(20)]],
    lrdate: [],
    ckbillno: [null, [Validators.maxLength(30)]],
    f1: [null, [Validators.maxLength(10)]],
    f2: [null, [Validators.maxLength(10)]],
    f1empn: [null, [Validators.maxLength(50)]],
    f2empn: [null, [Validators.maxLength(50)]],
    f1sj: [],
    f2sj: [],
  });

  constructor(protected dDbService: DDbService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dDb }) => {
      if (dDb.id === undefined) {
        const today = dayjs().startOf('day');
        dDb.dbdate = today;
        dDb.lrdate = today;
        dDb.f1sj = today;
        dDb.f2sj = today;
      }

      this.updateForm(dDb);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dDb = this.createFromForm();
    if (dDb.id !== undefined) {
      this.subscribeToSaveResponse(this.dDbService.update(dDb));
    } else {
      this.subscribeToSaveResponse(this.dDbService.create(dDb));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDDb>>): void {
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

  protected updateForm(dDb: IDDb): void {
    this.editForm.patchValue({
      id: dDb.id,
      dbdate: dDb.dbdate ? dDb.dbdate.format(DATE_TIME_FORMAT) : null,
      dbbillno: dDb.dbbillno,
      rdepot: dDb.rdepot,
      cdepot: dDb.cdepot,
      jbr: dDb.jbr,
      remark: dDb.remark,
      spbm: dDb.spbm,
      spmc: dDb.spmc,
      unit: dDb.unit,
      price: dDb.price,
      sl: dDb.sl,
      je: dDb.je,
      memo: dDb.memo,
      flag: dDb.flag,
      kcid: dDb.kcid,
      empn: dDb.empn,
      lrdate: dDb.lrdate ? dDb.lrdate.format(DATE_TIME_FORMAT) : null,
      ckbillno: dDb.ckbillno,
      f1: dDb.f1,
      f2: dDb.f2,
      f1empn: dDb.f1empn,
      f2empn: dDb.f2empn,
      f1sj: dDb.f1sj ? dDb.f1sj.format(DATE_TIME_FORMAT) : null,
      f2sj: dDb.f2sj ? dDb.f2sj.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IDDb {
    return {
      ...new DDb(),
      id: this.editForm.get(['id'])!.value,
      dbdate: this.editForm.get(['dbdate'])!.value ? dayjs(this.editForm.get(['dbdate'])!.value, DATE_TIME_FORMAT) : undefined,
      dbbillno: this.editForm.get(['dbbillno'])!.value,
      rdepot: this.editForm.get(['rdepot'])!.value,
      cdepot: this.editForm.get(['cdepot'])!.value,
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
      kcid: this.editForm.get(['kcid'])!.value,
      empn: this.editForm.get(['empn'])!.value,
      lrdate: this.editForm.get(['lrdate'])!.value ? dayjs(this.editForm.get(['lrdate'])!.value, DATE_TIME_FORMAT) : undefined,
      ckbillno: this.editForm.get(['ckbillno'])!.value,
      f1: this.editForm.get(['f1'])!.value,
      f2: this.editForm.get(['f2'])!.value,
      f1empn: this.editForm.get(['f1empn'])!.value,
      f2empn: this.editForm.get(['f2empn'])!.value,
      f1sj: this.editForm.get(['f1sj'])!.value ? dayjs(this.editForm.get(['f1sj'])!.value, DATE_TIME_FORMAT) : undefined,
      f2sj: this.editForm.get(['f2sj'])!.value ? dayjs(this.editForm.get(['f2sj'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
