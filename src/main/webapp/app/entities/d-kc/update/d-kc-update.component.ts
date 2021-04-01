import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDKc, DKc } from '../d-kc.model';
import { DKcService } from '../service/d-kc.service';

@Component({
  selector: 'jhi-d-kc-update',
  templateUrl: './d-kc-update.component.html',
})
export class DKcUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    depot: [null, [Validators.required, Validators.maxLength(20)]],
    spbm: [null, [Validators.required, Validators.maxLength(20)]],
    spmc: [null, [Validators.required, Validators.maxLength(50)]],
    xh: [null, [Validators.maxLength(50)]],
    dw: [null, [Validators.maxLength(20)]],
    price: [],
    sl: [],
    je: [],
  });

  constructor(protected dKcService: DKcService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dKc }) => {
      this.updateForm(dKc);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dKc = this.createFromForm();
    if (dKc.id !== undefined) {
      this.subscribeToSaveResponse(this.dKcService.update(dKc));
    } else {
      this.subscribeToSaveResponse(this.dKcService.create(dKc));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDKc>>): void {
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

  protected updateForm(dKc: IDKc): void {
    this.editForm.patchValue({
      id: dKc.id,
      depot: dKc.depot,
      spbm: dKc.spbm,
      spmc: dKc.spmc,
      xh: dKc.xh,
      dw: dKc.dw,
      price: dKc.price,
      sl: dKc.sl,
      je: dKc.je,
    });
  }

  protected createFromForm(): IDKc {
    return {
      ...new DKc(),
      id: this.editForm.get(['id'])!.value,
      depot: this.editForm.get(['depot'])!.value,
      spbm: this.editForm.get(['spbm'])!.value,
      spmc: this.editForm.get(['spmc'])!.value,
      xh: this.editForm.get(['xh'])!.value,
      dw: this.editForm.get(['dw'])!.value,
      price: this.editForm.get(['price'])!.value,
      sl: this.editForm.get(['sl'])!.value,
      je: this.editForm.get(['je'])!.value,
    };
  }
}
