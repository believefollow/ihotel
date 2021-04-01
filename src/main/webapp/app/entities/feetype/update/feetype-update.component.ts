import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IFeetype, Feetype } from '../feetype.model';
import { FeetypeService } from '../service/feetype.service';

@Component({
  selector: 'jhi-feetype-update',
  templateUrl: './feetype-update.component.html',
})
export class FeetypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    feenum: [null, [Validators.required]],
    feename: [null, [Validators.required, Validators.maxLength(45)]],
    price: [null, [Validators.required]],
    sign: [null, [Validators.required]],
    beizhu: [null, [Validators.maxLength(45)]],
    pym: [null, [Validators.maxLength(45)]],
    salespotn: [null, [Validators.required]],
    depot: [null, [Validators.maxLength(50)]],
    cbsign: [],
    ordersign: [],
    hoteldm: [null, [Validators.maxLength(20)]],
    isnew: [],
    ygj: [],
    autosign: [null, [Validators.maxLength(2)]],
    jj: [],
    hyj: [],
    dqkc: [],
  });

  constructor(protected feetypeService: FeetypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feetype }) => {
      this.updateForm(feetype);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const feetype = this.createFromForm();
    if (feetype.id !== undefined) {
      this.subscribeToSaveResponse(this.feetypeService.update(feetype));
    } else {
      this.subscribeToSaveResponse(this.feetypeService.create(feetype));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeetype>>): void {
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

  protected updateForm(feetype: IFeetype): void {
    this.editForm.patchValue({
      id: feetype.id,
      feenum: feetype.feenum,
      feename: feetype.feename,
      price: feetype.price,
      sign: feetype.sign,
      beizhu: feetype.beizhu,
      pym: feetype.pym,
      salespotn: feetype.salespotn,
      depot: feetype.depot,
      cbsign: feetype.cbsign,
      ordersign: feetype.ordersign,
      hoteldm: feetype.hoteldm,
      isnew: feetype.isnew,
      ygj: feetype.ygj,
      autosign: feetype.autosign,
      jj: feetype.jj,
      hyj: feetype.hyj,
      dqkc: feetype.dqkc,
    });
  }

  protected createFromForm(): IFeetype {
    return {
      ...new Feetype(),
      id: this.editForm.get(['id'])!.value,
      feenum: this.editForm.get(['feenum'])!.value,
      feename: this.editForm.get(['feename'])!.value,
      price: this.editForm.get(['price'])!.value,
      sign: this.editForm.get(['sign'])!.value,
      beizhu: this.editForm.get(['beizhu'])!.value,
      pym: this.editForm.get(['pym'])!.value,
      salespotn: this.editForm.get(['salespotn'])!.value,
      depot: this.editForm.get(['depot'])!.value,
      cbsign: this.editForm.get(['cbsign'])!.value,
      ordersign: this.editForm.get(['ordersign'])!.value,
      hoteldm: this.editForm.get(['hoteldm'])!.value,
      isnew: this.editForm.get(['isnew'])!.value,
      ygj: this.editForm.get(['ygj'])!.value,
      autosign: this.editForm.get(['autosign'])!.value,
      jj: this.editForm.get(['jj'])!.value,
      hyj: this.editForm.get(['hyj'])!.value,
      dqkc: this.editForm.get(['dqkc'])!.value,
    };
  }
}
