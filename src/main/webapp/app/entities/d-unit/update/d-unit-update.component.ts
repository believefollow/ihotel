import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDUnit, DUnit } from '../d-unit.model';
import { DUnitService } from '../service/d-unit.service';

@Component({
  selector: 'jhi-d-unit-update',
  templateUrl: './d-unit-update.component.html',
})
export class DUnitUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    unit: [null, [Validators.required, Validators.maxLength(20)]],
  });

  constructor(protected dUnitService: DUnitService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dUnit }) => {
      this.updateForm(dUnit);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dUnit = this.createFromForm();
    if (dUnit.id !== undefined) {
      this.subscribeToSaveResponse(this.dUnitService.update(dUnit));
    } else {
      this.subscribeToSaveResponse(this.dUnitService.create(dUnit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDUnit>>): void {
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

  protected updateForm(dUnit: IDUnit): void {
    this.editForm.patchValue({
      id: dUnit.id,
      unit: dUnit.unit,
    });
  }

  protected createFromForm(): IDUnit {
    return {
      ...new DUnit(),
      id: this.editForm.get(['id'])!.value,
      unit: this.editForm.get(['unit'])!.value,
    };
  }
}
