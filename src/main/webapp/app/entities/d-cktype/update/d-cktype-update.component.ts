import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDCktype, DCktype } from '../d-cktype.model';
import { DCktypeService } from '../service/d-cktype.service';

@Component({
  selector: 'jhi-d-cktype-update',
  templateUrl: './d-cktype-update.component.html',
})
export class DCktypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    cktype: [null, [Validators.required, Validators.maxLength(30)]],
    memo: [null, [Validators.maxLength(100)]],
    sign: [null, [Validators.required, Validators.maxLength(20)]],
  });

  constructor(protected dCktypeService: DCktypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dCktype }) => {
      this.updateForm(dCktype);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dCktype = this.createFromForm();
    if (dCktype.id !== undefined) {
      this.subscribeToSaveResponse(this.dCktypeService.update(dCktype));
    } else {
      this.subscribeToSaveResponse(this.dCktypeService.create(dCktype));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDCktype>>): void {
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

  protected updateForm(dCktype: IDCktype): void {
    this.editForm.patchValue({
      id: dCktype.id,
      cktype: dCktype.cktype,
      memo: dCktype.memo,
      sign: dCktype.sign,
    });
  }

  protected createFromForm(): IDCktype {
    return {
      ...new DCktype(),
      id: this.editForm.get(['id'])!.value,
      cktype: this.editForm.get(['cktype'])!.value,
      memo: this.editForm.get(['memo'])!.value,
      sign: this.editForm.get(['sign'])!.value,
    };
  }
}
