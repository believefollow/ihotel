import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDEmpn, DEmpn } from '../d-empn.model';
import { DEmpnService } from '../service/d-empn.service';

@Component({
  selector: 'jhi-d-empn-update',
  templateUrl: './d-empn-update.component.html',
})
export class DEmpnUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    empnid: [null, [Validators.required]],
    empn: [null, [Validators.required, Validators.maxLength(50)]],
    deptid: [],
    phone: [null, [Validators.maxLength(50)]],
  });

  constructor(protected dEmpnService: DEmpnService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dEmpn }) => {
      this.updateForm(dEmpn);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dEmpn = this.createFromForm();
    if (dEmpn.id !== undefined) {
      this.subscribeToSaveResponse(this.dEmpnService.update(dEmpn));
    } else {
      this.subscribeToSaveResponse(this.dEmpnService.create(dEmpn));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDEmpn>>): void {
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

  protected updateForm(dEmpn: IDEmpn): void {
    this.editForm.patchValue({
      id: dEmpn.id,
      empnid: dEmpn.empnid,
      empn: dEmpn.empn,
      deptid: dEmpn.deptid,
      phone: dEmpn.phone,
    });
  }

  protected createFromForm(): IDEmpn {
    return {
      ...new DEmpn(),
      id: this.editForm.get(['id'])!.value,
      empnid: this.editForm.get(['empnid'])!.value,
      empn: this.editForm.get(['empn'])!.value,
      deptid: this.editForm.get(['deptid'])!.value,
      phone: this.editForm.get(['phone'])!.value,
    };
  }
}
