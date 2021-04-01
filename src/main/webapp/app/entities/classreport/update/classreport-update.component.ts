import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IClassreport, Classreport } from '../classreport.model';
import { ClassreportService } from '../service/classreport.service';

@Component({
  selector: 'jhi-classreport-update',
  templateUrl: './classreport-update.component.html',
})
export class ClassreportUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    empn: [null, [Validators.required, Validators.maxLength(10)]],
    dt: [null, [Validators.required]],
    xjUp: [],
    yfjA: [],
    yfjD: [],
    gz: [],
    zz: [],
    zzYj: [],
    zzJs: [],
    zzTc: [],
    ff: [],
    minibar: [],
    phone: [],
    other: [],
    pc: [],
    cz: [],
    cy: [],
    md: [],
    huiy: [],
    dtb: [],
    sszx: [],
    cyz: [],
    hoteldm: [null, [Validators.maxLength(20)]],
    gzxj: [],
    isnew: [],
  });

  constructor(protected classreportService: ClassreportService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classreport }) => {
      if (classreport.id === undefined) {
        const today = dayjs().startOf('day');
        classreport.dt = today;
      }

      this.updateForm(classreport);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const classreport = this.createFromForm();
    if (classreport.id !== undefined) {
      this.subscribeToSaveResponse(this.classreportService.update(classreport));
    } else {
      this.subscribeToSaveResponse(this.classreportService.create(classreport));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassreport>>): void {
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

  protected updateForm(classreport: IClassreport): void {
    this.editForm.patchValue({
      id: classreport.id,
      empn: classreport.empn,
      dt: classreport.dt ? classreport.dt.format(DATE_TIME_FORMAT) : null,
      xjUp: classreport.xjUp,
      yfjA: classreport.yfjA,
      yfjD: classreport.yfjD,
      gz: classreport.gz,
      zz: classreport.zz,
      zzYj: classreport.zzYj,
      zzJs: classreport.zzJs,
      zzTc: classreport.zzTc,
      ff: classreport.ff,
      minibar: classreport.minibar,
      phone: classreport.phone,
      other: classreport.other,
      pc: classreport.pc,
      cz: classreport.cz,
      cy: classreport.cy,
      md: classreport.md,
      huiy: classreport.huiy,
      dtb: classreport.dtb,
      sszx: classreport.sszx,
      cyz: classreport.cyz,
      hoteldm: classreport.hoteldm,
      gzxj: classreport.gzxj,
      isnew: classreport.isnew,
    });
  }

  protected createFromForm(): IClassreport {
    return {
      ...new Classreport(),
      id: this.editForm.get(['id'])!.value,
      empn: this.editForm.get(['empn'])!.value,
      dt: this.editForm.get(['dt'])!.value ? dayjs(this.editForm.get(['dt'])!.value, DATE_TIME_FORMAT) : undefined,
      xjUp: this.editForm.get(['xjUp'])!.value,
      yfjA: this.editForm.get(['yfjA'])!.value,
      yfjD: this.editForm.get(['yfjD'])!.value,
      gz: this.editForm.get(['gz'])!.value,
      zz: this.editForm.get(['zz'])!.value,
      zzYj: this.editForm.get(['zzYj'])!.value,
      zzJs: this.editForm.get(['zzJs'])!.value,
      zzTc: this.editForm.get(['zzTc'])!.value,
      ff: this.editForm.get(['ff'])!.value,
      minibar: this.editForm.get(['minibar'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      other: this.editForm.get(['other'])!.value,
      pc: this.editForm.get(['pc'])!.value,
      cz: this.editForm.get(['cz'])!.value,
      cy: this.editForm.get(['cy'])!.value,
      md: this.editForm.get(['md'])!.value,
      huiy: this.editForm.get(['huiy'])!.value,
      dtb: this.editForm.get(['dtb'])!.value,
      sszx: this.editForm.get(['sszx'])!.value,
      cyz: this.editForm.get(['cyz'])!.value,
      hoteldm: this.editForm.get(['hoteldm'])!.value,
      gzxj: this.editForm.get(['gzxj'])!.value,
      isnew: this.editForm.get(['isnew'])!.value,
    };
  }
}
