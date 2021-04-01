import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAccPp, AccPp } from '../acc-pp.model';
import { AccPpService } from '../service/acc-pp.service';

@Component({
  selector: 'jhi-acc-pp-update',
  templateUrl: './acc-pp-update.component.html',
})
export class AccPpUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    acc: [null, [Validators.maxLength(50)]],
  });

  constructor(protected accPpService: AccPpService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accPp }) => {
      this.updateForm(accPp);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accPp = this.createFromForm();
    if (accPp.id !== undefined) {
      this.subscribeToSaveResponse(this.accPpService.update(accPp));
    } else {
      this.subscribeToSaveResponse(this.accPpService.create(accPp));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccPp>>): void {
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

  protected updateForm(accPp: IAccPp): void {
    this.editForm.patchValue({
      id: accPp.id,
      acc: accPp.acc,
    });
  }

  protected createFromForm(): IAccPp {
    return {
      ...new AccPp(),
      id: this.editForm.get(['id'])!.value,
      acc: this.editForm.get(['acc'])!.value,
    };
  }
}
