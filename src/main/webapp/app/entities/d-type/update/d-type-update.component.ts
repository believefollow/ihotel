import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDType, DType } from '../d-type.model';
import { DTypeService } from '../service/d-type.service';

@Component({
  selector: 'jhi-d-type-update',
  templateUrl: './d-type-update.component.html',
})
export class DTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    typeid: [null, [Validators.required]],
    typename: [null, [Validators.required, Validators.maxLength(50)]],
    fatherid: [null, [Validators.required]],
    disabled: [],
  });

  constructor(protected dTypeService: DTypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dType }) => {
      this.updateForm(dType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dType = this.createFromForm();
    if (dType.id !== undefined) {
      this.subscribeToSaveResponse(this.dTypeService.update(dType));
    } else {
      this.subscribeToSaveResponse(this.dTypeService.create(dType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDType>>): void {
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

  protected updateForm(dType: IDType): void {
    this.editForm.patchValue({
      id: dType.id,
      typeid: dType.typeid,
      typename: dType.typename,
      fatherid: dType.fatherid,
      disabled: dType.disabled,
    });
  }

  protected createFromForm(): IDType {
    return {
      ...new DType(),
      id: this.editForm.get(['id'])!.value,
      typeid: this.editForm.get(['typeid'])!.value,
      typename: this.editForm.get(['typename'])!.value,
      fatherid: this.editForm.get(['fatherid'])!.value,
      disabled: this.editForm.get(['disabled'])!.value,
    };
  }
}
