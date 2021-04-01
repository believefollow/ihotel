import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDxSed, DxSed } from '../dx-sed.model';
import { DxSedService } from '../service/dx-sed.service';

@Component({
  selector: 'jhi-dx-sed-update',
  templateUrl: './dx-sed-update.component.html',
})
export class DxSedUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    dxRq: [],
    dxZt: [null, [Validators.maxLength(2)]],
    fsSj: [],
  });

  constructor(protected dxSedService: DxSedService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dxSed }) => {
      if (dxSed.id === undefined) {
        const today = dayjs().startOf('day');
        dxSed.dxRq = today;
        dxSed.fsSj = today;
      }

      this.updateForm(dxSed);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dxSed = this.createFromForm();
    if (dxSed.id !== undefined) {
      this.subscribeToSaveResponse(this.dxSedService.update(dxSed));
    } else {
      this.subscribeToSaveResponse(this.dxSedService.create(dxSed));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDxSed>>): void {
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

  protected updateForm(dxSed: IDxSed): void {
    this.editForm.patchValue({
      id: dxSed.id,
      dxRq: dxSed.dxRq ? dxSed.dxRq.format(DATE_TIME_FORMAT) : null,
      dxZt: dxSed.dxZt,
      fsSj: dxSed.fsSj ? dxSed.fsSj.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IDxSed {
    return {
      ...new DxSed(),
      id: this.editForm.get(['id'])!.value,
      dxRq: this.editForm.get(['dxRq'])!.value ? dayjs(this.editForm.get(['dxRq'])!.value, DATE_TIME_FORMAT) : undefined,
      dxZt: this.editForm.get(['dxZt'])!.value,
      fsSj: this.editForm.get(['fsSj'])!.value ? dayjs(this.editForm.get(['fsSj'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
