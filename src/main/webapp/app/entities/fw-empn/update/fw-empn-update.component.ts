import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IFwEmpn, FwEmpn } from '../fw-empn.model';
import { FwEmpnService } from '../service/fw-empn.service';

@Component({
  selector: 'jhi-fw-empn-update',
  templateUrl: './fw-empn-update.component.html',
})
export class FwEmpnUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    empnid: [null, [Validators.required, Validators.maxLength(50)]],
    empn: [null, [Validators.required, Validators.maxLength(50)]],
    deptid: [],
    phone: [null, [Validators.maxLength(50)]],
  });

  constructor(protected fwEmpnService: FwEmpnService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fwEmpn }) => {
      this.updateForm(fwEmpn);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fwEmpn = this.createFromForm();
    if (fwEmpn.id !== undefined) {
      this.subscribeToSaveResponse(this.fwEmpnService.update(fwEmpn));
    } else {
      this.subscribeToSaveResponse(this.fwEmpnService.create(fwEmpn));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFwEmpn>>): void {
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

  protected updateForm(fwEmpn: IFwEmpn): void {
    this.editForm.patchValue({
      id: fwEmpn.id,
      empnid: fwEmpn.empnid,
      empn: fwEmpn.empn,
      deptid: fwEmpn.deptid,
      phone: fwEmpn.phone,
    });
  }

  protected createFromForm(): IFwEmpn {
    return {
      ...new FwEmpn(),
      id: this.editForm.get(['id'])!.value,
      empnid: this.editForm.get(['empnid'])!.value,
      empn: this.editForm.get(['empn'])!.value,
      deptid: this.editForm.get(['deptid'])!.value,
      phone: this.editForm.get(['phone'])!.value,
    };
  }
}
