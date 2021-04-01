import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDDept, DDept } from '../d-dept.model';
import { DDeptService } from '../service/d-dept.service';

@Component({
  selector: 'jhi-d-dept-update',
  templateUrl: './d-dept-update.component.html',
})
export class DDeptUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    deptid: [null, [Validators.required]],
    deptname: [null, [Validators.required, Validators.maxLength(50)]],
  });

  constructor(protected dDeptService: DDeptService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dDept }) => {
      this.updateForm(dDept);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dDept = this.createFromForm();
    if (dDept.id !== undefined) {
      this.subscribeToSaveResponse(this.dDeptService.update(dDept));
    } else {
      this.subscribeToSaveResponse(this.dDeptService.create(dDept));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDDept>>): void {
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

  protected updateForm(dDept: IDDept): void {
    this.editForm.patchValue({
      id: dDept.id,
      deptid: dDept.deptid,
      deptname: dDept.deptname,
    });
  }

  protected createFromForm(): IDDept {
    return {
      ...new DDept(),
      id: this.editForm.get(['id'])!.value,
      deptid: this.editForm.get(['deptid'])!.value,
      deptname: this.editForm.get(['deptname'])!.value,
    };
  }
}
