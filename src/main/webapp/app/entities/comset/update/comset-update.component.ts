import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IComset, Comset } from '../comset.model';
import { ComsetService } from '../service/comset.service';

@Component({
  selector: 'jhi-comset-update',
  templateUrl: './comset-update.component.html',
})
export class ComsetUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    comNum: [null, [Validators.required, Validators.maxLength(10)]],
    comBytes: [null, [Validators.required, Validators.maxLength(18)]],
    comDatabit: [null, [Validators.required, Validators.maxLength(10)]],
    comParitycheck: [null, [Validators.required, Validators.maxLength(10)]],
    comStopbit: [null, [Validators.required, Validators.maxLength(18)]],
    comFunction: [null, [Validators.required]],
  });

  constructor(protected comsetService: ComsetService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comset }) => {
      this.updateForm(comset);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comset = this.createFromForm();
    if (comset.id !== undefined) {
      this.subscribeToSaveResponse(this.comsetService.update(comset));
    } else {
      this.subscribeToSaveResponse(this.comsetService.create(comset));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComset>>): void {
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

  protected updateForm(comset: IComset): void {
    this.editForm.patchValue({
      id: comset.id,
      comNum: comset.comNum,
      comBytes: comset.comBytes,
      comDatabit: comset.comDatabit,
      comParitycheck: comset.comParitycheck,
      comStopbit: comset.comStopbit,
      comFunction: comset.comFunction,
    });
  }

  protected createFromForm(): IComset {
    return {
      ...new Comset(),
      id: this.editForm.get(['id'])!.value,
      comNum: this.editForm.get(['comNum'])!.value,
      comBytes: this.editForm.get(['comBytes'])!.value,
      comDatabit: this.editForm.get(['comDatabit'])!.value,
      comParitycheck: this.editForm.get(['comParitycheck'])!.value,
      comStopbit: this.editForm.get(['comStopbit'])!.value,
      comFunction: this.editForm.get(['comFunction'])!.value,
    };
  }
}
