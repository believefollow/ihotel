import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDGoods, DGoods } from '../d-goods.model';
import { DGoodsService } from '../service/d-goods.service';

@Component({
  selector: 'jhi-d-goods-update',
  templateUrl: './d-goods-update.component.html',
})
export class DGoodsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    typeid: [null, [Validators.required]],
    goodsname: [null, [Validators.required, Validators.maxLength(100)]],
    goodsid: [null, [Validators.required, Validators.maxLength(20)]],
    ggxh: [null, [Validators.maxLength(50)]],
    pysj: [null, [Validators.maxLength(50)]],
    wbsj: [null, [Validators.maxLength(50)]],
    unit: [null, [Validators.maxLength(50)]],
    gcsl: [],
    dcsl: [],
    remark: [null, [Validators.maxLength(50)]],
  });

  constructor(protected dGoodsService: DGoodsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dGoods }) => {
      this.updateForm(dGoods);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dGoods = this.createFromForm();
    if (dGoods.id !== undefined) {
      this.subscribeToSaveResponse(this.dGoodsService.update(dGoods));
    } else {
      this.subscribeToSaveResponse(this.dGoodsService.create(dGoods));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDGoods>>): void {
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

  protected updateForm(dGoods: IDGoods): void {
    this.editForm.patchValue({
      id: dGoods.id,
      typeid: dGoods.typeid,
      goodsname: dGoods.goodsname,
      goodsid: dGoods.goodsid,
      ggxh: dGoods.ggxh,
      pysj: dGoods.pysj,
      wbsj: dGoods.wbsj,
      unit: dGoods.unit,
      gcsl: dGoods.gcsl,
      dcsl: dGoods.dcsl,
      remark: dGoods.remark,
    });
  }

  protected createFromForm(): IDGoods {
    return {
      ...new DGoods(),
      id: this.editForm.get(['id'])!.value,
      typeid: this.editForm.get(['typeid'])!.value,
      goodsname: this.editForm.get(['goodsname'])!.value,
      goodsid: this.editForm.get(['goodsid'])!.value,
      ggxh: this.editForm.get(['ggxh'])!.value,
      pysj: this.editForm.get(['pysj'])!.value,
      wbsj: this.editForm.get(['wbsj'])!.value,
      unit: this.editForm.get(['unit'])!.value,
      gcsl: this.editForm.get(['gcsl'])!.value,
      dcsl: this.editForm.get(['dcsl'])!.value,
      remark: this.editForm.get(['remark'])!.value,
    };
  }
}
