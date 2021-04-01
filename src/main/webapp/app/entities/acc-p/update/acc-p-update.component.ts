import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAccP, AccP } from '../acc-p.model';
import { AccPService } from '../service/acc-p.service';

@Component({
  selector: 'jhi-acc-p-update',
  templateUrl: './acc-p-update.component.html',
})
export class AccPUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    acc: [null, [Validators.required, Validators.maxLength(50)]],
  });

  constructor(protected accPService: AccPService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accP }) => {
      this.updateForm(accP);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accP = this.createFromForm();
    if (accP.id !== undefined) {
      this.subscribeToSaveResponse(this.accPService.update(accP));
    } else {
      this.subscribeToSaveResponse(this.accPService.create(accP));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccP>>): void {
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

  protected updateForm(accP: IAccP): void {
    this.editForm.patchValue({
      id: accP.id,
      acc: accP.acc,
    });
  }

  protected createFromForm(): IAccP {
    return {
      ...new AccP(),
      id: this.editForm.get(['id'])!.value,
      acc: this.editForm.get(['acc'])!.value,
    };
  }
}
