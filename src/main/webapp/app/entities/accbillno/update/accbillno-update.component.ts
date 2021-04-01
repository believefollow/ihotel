import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAccbillno, Accbillno } from '../accbillno.model';
import { AccbillnoService } from '../service/accbillno.service';

@Component({
  selector: 'jhi-accbillno-update',
  templateUrl: './accbillno-update.component.html',
})
export class AccbillnoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    account: [null, [Validators.required, Validators.maxLength(30)]],
    accbillno: [null, [Validators.maxLength(30)]],
  });

  constructor(protected accbillnoService: AccbillnoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accbillno }) => {
      this.updateForm(accbillno);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accbillno = this.createFromForm();
    if (accbillno.id !== undefined) {
      this.subscribeToSaveResponse(this.accbillnoService.update(accbillno));
    } else {
      this.subscribeToSaveResponse(this.accbillnoService.create(accbillno));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccbillno>>): void {
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

  protected updateForm(accbillno: IAccbillno): void {
    this.editForm.patchValue({
      id: accbillno.id,
      account: accbillno.account,
      accbillno: accbillno.accbillno,
    });
  }

  protected createFromForm(): IAccbillno {
    return {
      ...new Accbillno(),
      id: this.editForm.get(['id'])!.value,
      account: this.editForm.get(['account'])!.value,
      accbillno: this.editForm.get(['accbillno'])!.value,
    };
  }
}
