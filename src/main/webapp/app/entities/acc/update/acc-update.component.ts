import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAcc, Acc } from '../acc.model';
import { AccService } from '../service/acc.service';

@Component({
  selector: 'jhi-acc-update',
  templateUrl: './acc-update.component.html',
})
export class AccUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    acc: [null, [Validators.maxLength(50)]],
  });

  constructor(protected accService: AccService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ acc }) => {
      this.updateForm(acc);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const acc = this.createFromForm();
    if (acc.id !== undefined) {
      this.subscribeToSaveResponse(this.accService.update(acc));
    } else {
      this.subscribeToSaveResponse(this.accService.create(acc));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAcc>>): void {
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

  protected updateForm(acc: IAcc): void {
    this.editForm.patchValue({
      id: acc.id,
      acc: acc.acc,
    });
  }

  protected createFromForm(): IAcc {
    return {
      ...new Acc(),
      id: this.editForm.get(['id'])!.value,
      acc: this.editForm.get(['acc'])!.value,
    };
  }
}
