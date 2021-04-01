import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IClassRename, ClassRename } from '../class-rename.model';
import { ClassRenameService } from '../service/class-rename.service';

@Component({
  selector: 'jhi-class-rename-update',
  templateUrl: './class-rename-update.component.html',
})
export class ClassRenameUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    dt: [null, [Validators.required]],
    empn: [null, [Validators.required, Validators.maxLength(10)]],
    oldmoney: [],
    getmoney: [],
    toup: [],
    downempn: [null, [Validators.maxLength(10)]],
    todown: [],
    flag: [],
    old2: [],
    get2: [],
    toup2: [],
    todown2: [],
    upempn2: [null, [Validators.maxLength(10)]],
    im9008: [],
    im9009: [],
    co9991: [],
    co9992: [],
    co9993: [],
    co9994: [],
    co9995: [],
    co9998: [],
    im9007: [],
    gotime: [],
    co9999: [],
    cm9008: [],
    cm9009: [],
    co99910: [],
    checkSign: [null, [Validators.maxLength(2)]],
    classPb: [null, [Validators.maxLength(10)]],
    ck: [],
    dk: [],
    sjrq: [],
    qsjrq: [],
    byje: [],
    xfcw: [null, [Validators.maxLength(30)]],
    hoteldm: [null, [Validators.maxLength(20)]],
    isnew: [],
    co99912: [],
    xj: [],
    classname: [null, [Validators.maxLength(50)]],
    co9010: [],
    co9012: [],
    co9013: [],
    co9014: [],
    co99915: [],
    hyxj: [],
    hysk: [],
    hyqt: [],
    hkxj: [],
    hksk: [],
    hkqt: [],
    qtwt: [],
    qtysq: [],
    bbysj: [],
    zfbje: [],
    wxje: [],
    w99920: [],
    z99921: [],
  });

  constructor(protected classRenameService: ClassRenameService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classRename }) => {
      if (classRename.id === undefined) {
        const today = dayjs().startOf('day');
        classRename.dt = today;
        classRename.gotime = today;
        classRename.sjrq = today;
        classRename.qsjrq = today;
      }

      this.updateForm(classRename);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const classRename = this.createFromForm();
    if (classRename.id !== undefined) {
      this.subscribeToSaveResponse(this.classRenameService.update(classRename));
    } else {
      this.subscribeToSaveResponse(this.classRenameService.create(classRename));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassRename>>): void {
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

  protected updateForm(classRename: IClassRename): void {
    this.editForm.patchValue({
      id: classRename.id,
      dt: classRename.dt ? classRename.dt.format(DATE_TIME_FORMAT) : null,
      empn: classRename.empn,
      oldmoney: classRename.oldmoney,
      getmoney: classRename.getmoney,
      toup: classRename.toup,
      downempn: classRename.downempn,
      todown: classRename.todown,
      flag: classRename.flag,
      old2: classRename.old2,
      get2: classRename.get2,
      toup2: classRename.toup2,
      todown2: classRename.todown2,
      upempn2: classRename.upempn2,
      im9008: classRename.im9008,
      im9009: classRename.im9009,
      co9991: classRename.co9991,
      co9992: classRename.co9992,
      co9993: classRename.co9993,
      co9994: classRename.co9994,
      co9995: classRename.co9995,
      co9998: classRename.co9998,
      im9007: classRename.im9007,
      gotime: classRename.gotime ? classRename.gotime.format(DATE_TIME_FORMAT) : null,
      co9999: classRename.co9999,
      cm9008: classRename.cm9008,
      cm9009: classRename.cm9009,
      co99910: classRename.co99910,
      checkSign: classRename.checkSign,
      classPb: classRename.classPb,
      ck: classRename.ck,
      dk: classRename.dk,
      sjrq: classRename.sjrq ? classRename.sjrq.format(DATE_TIME_FORMAT) : null,
      qsjrq: classRename.qsjrq ? classRename.qsjrq.format(DATE_TIME_FORMAT) : null,
      byje: classRename.byje,
      xfcw: classRename.xfcw,
      hoteldm: classRename.hoteldm,
      isnew: classRename.isnew,
      co99912: classRename.co99912,
      xj: classRename.xj,
      classname: classRename.classname,
      co9010: classRename.co9010,
      co9012: classRename.co9012,
      co9013: classRename.co9013,
      co9014: classRename.co9014,
      co99915: classRename.co99915,
      hyxj: classRename.hyxj,
      hysk: classRename.hysk,
      hyqt: classRename.hyqt,
      hkxj: classRename.hkxj,
      hksk: classRename.hksk,
      hkqt: classRename.hkqt,
      qtwt: classRename.qtwt,
      qtysq: classRename.qtysq,
      bbysj: classRename.bbysj,
      zfbje: classRename.zfbje,
      wxje: classRename.wxje,
      w99920: classRename.w99920,
      z99921: classRename.z99921,
    });
  }

  protected createFromForm(): IClassRename {
    return {
      ...new ClassRename(),
      id: this.editForm.get(['id'])!.value,
      dt: this.editForm.get(['dt'])!.value ? dayjs(this.editForm.get(['dt'])!.value, DATE_TIME_FORMAT) : undefined,
      empn: this.editForm.get(['empn'])!.value,
      oldmoney: this.editForm.get(['oldmoney'])!.value,
      getmoney: this.editForm.get(['getmoney'])!.value,
      toup: this.editForm.get(['toup'])!.value,
      downempn: this.editForm.get(['downempn'])!.value,
      todown: this.editForm.get(['todown'])!.value,
      flag: this.editForm.get(['flag'])!.value,
      old2: this.editForm.get(['old2'])!.value,
      get2: this.editForm.get(['get2'])!.value,
      toup2: this.editForm.get(['toup2'])!.value,
      todown2: this.editForm.get(['todown2'])!.value,
      upempn2: this.editForm.get(['upempn2'])!.value,
      im9008: this.editForm.get(['im9008'])!.value,
      im9009: this.editForm.get(['im9009'])!.value,
      co9991: this.editForm.get(['co9991'])!.value,
      co9992: this.editForm.get(['co9992'])!.value,
      co9993: this.editForm.get(['co9993'])!.value,
      co9994: this.editForm.get(['co9994'])!.value,
      co9995: this.editForm.get(['co9995'])!.value,
      co9998: this.editForm.get(['co9998'])!.value,
      im9007: this.editForm.get(['im9007'])!.value,
      gotime: this.editForm.get(['gotime'])!.value ? dayjs(this.editForm.get(['gotime'])!.value, DATE_TIME_FORMAT) : undefined,
      co9999: this.editForm.get(['co9999'])!.value,
      cm9008: this.editForm.get(['cm9008'])!.value,
      cm9009: this.editForm.get(['cm9009'])!.value,
      co99910: this.editForm.get(['co99910'])!.value,
      checkSign: this.editForm.get(['checkSign'])!.value,
      classPb: this.editForm.get(['classPb'])!.value,
      ck: this.editForm.get(['ck'])!.value,
      dk: this.editForm.get(['dk'])!.value,
      sjrq: this.editForm.get(['sjrq'])!.value ? dayjs(this.editForm.get(['sjrq'])!.value, DATE_TIME_FORMAT) : undefined,
      qsjrq: this.editForm.get(['qsjrq'])!.value ? dayjs(this.editForm.get(['qsjrq'])!.value, DATE_TIME_FORMAT) : undefined,
      byje: this.editForm.get(['byje'])!.value,
      xfcw: this.editForm.get(['xfcw'])!.value,
      hoteldm: this.editForm.get(['hoteldm'])!.value,
      isnew: this.editForm.get(['isnew'])!.value,
      co99912: this.editForm.get(['co99912'])!.value,
      xj: this.editForm.get(['xj'])!.value,
      classname: this.editForm.get(['classname'])!.value,
      co9010: this.editForm.get(['co9010'])!.value,
      co9012: this.editForm.get(['co9012'])!.value,
      co9013: this.editForm.get(['co9013'])!.value,
      co9014: this.editForm.get(['co9014'])!.value,
      co99915: this.editForm.get(['co99915'])!.value,
      hyxj: this.editForm.get(['hyxj'])!.value,
      hysk: this.editForm.get(['hysk'])!.value,
      hyqt: this.editForm.get(['hyqt'])!.value,
      hkxj: this.editForm.get(['hkxj'])!.value,
      hksk: this.editForm.get(['hksk'])!.value,
      hkqt: this.editForm.get(['hkqt'])!.value,
      qtwt: this.editForm.get(['qtwt'])!.value,
      qtysq: this.editForm.get(['qtysq'])!.value,
      bbysj: this.editForm.get(['bbysj'])!.value,
      zfbje: this.editForm.get(['zfbje'])!.value,
      wxje: this.editForm.get(['wxje'])!.value,
      w99920: this.editForm.get(['w99920'])!.value,
      z99921: this.editForm.get(['z99921'])!.value,
    };
  }
}
