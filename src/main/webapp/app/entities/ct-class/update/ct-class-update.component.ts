import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICtClass, CtClass } from '../ct-class.model';
import { CtClassService } from '../service/ct-class.service';

@Component({
  selector: 'jhi-ct-class-update',
  templateUrl: './ct-class-update.component.html',
})
export class CtClassUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    dt: [null, [Validators.required]],
    empn: [null, [Validators.required, Validators.maxLength(10)]],
    flag: [],
    jbempn: [null, [Validators.maxLength(20)]],
    gotime: [],
    xj: [],
    zp: [],
    sk: [],
    hyk: [],
    cq: [],
    gz: [],
    gfz: [],
    yq: [],
    yh: [],
    zzh: [],
    checkSign: [null, [Validators.maxLength(2)]],
  });

  constructor(protected ctClassService: CtClassService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ctClass }) => {
      if (ctClass.id === undefined) {
        const today = dayjs().startOf('day');
        ctClass.dt = today;
        ctClass.gotime = today;
      }

      this.updateForm(ctClass);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ctClass = this.createFromForm();
    if (ctClass.id !== undefined) {
      this.subscribeToSaveResponse(this.ctClassService.update(ctClass));
    } else {
      this.subscribeToSaveResponse(this.ctClassService.create(ctClass));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICtClass>>): void {
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

  protected updateForm(ctClass: ICtClass): void {
    this.editForm.patchValue({
      id: ctClass.id,
      dt: ctClass.dt ? ctClass.dt.format(DATE_TIME_FORMAT) : null,
      empn: ctClass.empn,
      flag: ctClass.flag,
      jbempn: ctClass.jbempn,
      gotime: ctClass.gotime ? ctClass.gotime.format(DATE_TIME_FORMAT) : null,
      xj: ctClass.xj,
      zp: ctClass.zp,
      sk: ctClass.sk,
      hyk: ctClass.hyk,
      cq: ctClass.cq,
      gz: ctClass.gz,
      gfz: ctClass.gfz,
      yq: ctClass.yq,
      yh: ctClass.yh,
      zzh: ctClass.zzh,
      checkSign: ctClass.checkSign,
    });
  }

  protected createFromForm(): ICtClass {
    return {
      ...new CtClass(),
      id: this.editForm.get(['id'])!.value,
      dt: this.editForm.get(['dt'])!.value ? dayjs(this.editForm.get(['dt'])!.value, DATE_TIME_FORMAT) : undefined,
      empn: this.editForm.get(['empn'])!.value,
      flag: this.editForm.get(['flag'])!.value,
      jbempn: this.editForm.get(['jbempn'])!.value,
      gotime: this.editForm.get(['gotime'])!.value ? dayjs(this.editForm.get(['gotime'])!.value, DATE_TIME_FORMAT) : undefined,
      xj: this.editForm.get(['xj'])!.value,
      zp: this.editForm.get(['zp'])!.value,
      sk: this.editForm.get(['sk'])!.value,
      hyk: this.editForm.get(['hyk'])!.value,
      cq: this.editForm.get(['cq'])!.value,
      gz: this.editForm.get(['gz'])!.value,
      gfz: this.editForm.get(['gfz'])!.value,
      yq: this.editForm.get(['yq'])!.value,
      yh: this.editForm.get(['yh'])!.value,
      zzh: this.editForm.get(['zzh'])!.value,
      checkSign: this.editForm.get(['checkSign'])!.value,
    };
  }
}
