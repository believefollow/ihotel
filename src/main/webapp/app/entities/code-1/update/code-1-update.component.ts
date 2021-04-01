import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICode1, Code1 } from '../code-1.model';
import { Code1Service } from '../service/code-1.service';

@Component({
  selector: 'jhi-code-1-update',
  templateUrl: './code-1-update.component.html',
})
export class Code1UpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code1: [null, [Validators.required, Validators.maxLength(20)]],
    code2: [null, [Validators.required, Validators.maxLength(20)]],
  });

  constructor(protected code1Service: Code1Service, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ code1 }) => {
      this.updateForm(code1);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const code1 = this.createFromForm();
    if (code1.id !== undefined) {
      this.subscribeToSaveResponse(this.code1Service.update(code1));
    } else {
      this.subscribeToSaveResponse(this.code1Service.create(code1));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICode1>>): void {
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

  protected updateForm(code1: ICode1): void {
    this.editForm.patchValue({
      id: code1.id,
      code1: code1.code1,
      code2: code1.code2,
    });
  }

  protected createFromForm(): ICode1 {
    return {
      ...new Code1(),
      id: this.editForm.get(['id'])!.value,
      code1: this.editForm.get(['code1'])!.value,
      code2: this.editForm.get(['code2'])!.value,
    };
  }
}
