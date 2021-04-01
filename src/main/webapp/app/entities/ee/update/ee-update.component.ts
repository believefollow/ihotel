import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEe, Ee } from '../ee.model';
import { EeService } from '../service/ee.service';

@Component({
  selector: 'jhi-ee-update',
  templateUrl: './ee-update.component.html',
})
export class EeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    acc: [null, [Validators.maxLength(50)]],
  });

  constructor(protected eeService: EeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ee }) => {
      this.updateForm(ee);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ee = this.createFromForm();
    if (ee.id !== undefined) {
      this.subscribeToSaveResponse(this.eeService.update(ee));
    } else {
      this.subscribeToSaveResponse(this.eeService.create(ee));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEe>>): void {
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

  protected updateForm(ee: IEe): void {
    this.editForm.patchValue({
      id: ee.id,
      acc: ee.acc,
    });
  }

  protected createFromForm(): IEe {
    return {
      ...new Ee(),
      id: this.editForm.get(['id'])!.value,
      acc: this.editForm.get(['acc'])!.value,
    };
  }
}
