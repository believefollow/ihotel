import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICzCzl3, CzCzl3 } from '../cz-czl-3.model';
import { CzCzl3Service } from '../service/cz-czl-3.service';

@Component({
  selector: 'jhi-cz-czl-3-update',
  templateUrl: './cz-czl-3-update.component.html',
})
export class CzCzl3UpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    zfs: [],
    kfs: [],
    protocoln: [null, [Validators.maxLength(50)]],
    roomtype: [null, [Validators.maxLength(50)]],
    sl: [],
  });

  constructor(protected czCzl3Service: CzCzl3Service, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ czCzl3 }) => {
      this.updateForm(czCzl3);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const czCzl3 = this.createFromForm();
    if (czCzl3.id !== undefined) {
      this.subscribeToSaveResponse(this.czCzl3Service.update(czCzl3));
    } else {
      this.subscribeToSaveResponse(this.czCzl3Service.create(czCzl3));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICzCzl3>>): void {
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

  protected updateForm(czCzl3: ICzCzl3): void {
    this.editForm.patchValue({
      id: czCzl3.id,
      zfs: czCzl3.zfs,
      kfs: czCzl3.kfs,
      protocoln: czCzl3.protocoln,
      roomtype: czCzl3.roomtype,
      sl: czCzl3.sl,
    });
  }

  protected createFromForm(): ICzCzl3 {
    return {
      ...new CzCzl3(),
      id: this.editForm.get(['id'])!.value,
      zfs: this.editForm.get(['zfs'])!.value,
      kfs: this.editForm.get(['kfs'])!.value,
      protocoln: this.editForm.get(['protocoln'])!.value,
      roomtype: this.editForm.get(['roomtype'])!.value,
      sl: this.editForm.get(['sl'])!.value,
    };
  }
}
