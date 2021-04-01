import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAdhoc, Adhoc } from '../adhoc.model';
import { AdhocService } from '../service/adhoc.service';

@Component({
  selector: 'jhi-adhoc-update',
  templateUrl: './adhoc-update.component.html',
})
export class AdhocUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required, Validators.maxLength(10)]],
    remark: [null, [Validators.required, Validators.maxLength(40)]],
  });

  constructor(protected adhocService: AdhocService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adhoc }) => {
      this.updateForm(adhoc);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adhoc = this.createFromForm();
    if (adhoc.id !== undefined) {
      this.subscribeToSaveResponse(this.adhocService.update(adhoc));
    } else {
      this.subscribeToSaveResponse(this.adhocService.create(adhoc));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdhoc>>): void {
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

  protected updateForm(adhoc: IAdhoc): void {
    this.editForm.patchValue({
      id: adhoc.id,
      remark: adhoc.remark,
    });
  }

  protected createFromForm(): IAdhoc {
    return {
      ...new Adhoc(),
      id: this.editForm.get(['id'])!.value,
      remark: this.editForm.get(['remark'])!.value,
    };
  }
}
