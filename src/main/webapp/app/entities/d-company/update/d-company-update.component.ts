import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDCompany, DCompany } from '../d-company.model';
import { DCompanyService } from '../service/d-company.service';

@Component({
  selector: 'jhi-d-company-update',
  templateUrl: './d-company-update.component.html',
})
export class DCompanyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    company: [null, [Validators.required, Validators.maxLength(100)]],
    linkman: [null, [Validators.maxLength(50)]],
    phone: [null, [Validators.maxLength(100)]],
    address: [null, [Validators.maxLength(100)]],
    remark: [null, [Validators.maxLength(100)]],
    fax: [null, [Validators.maxLength(100)]],
    id: [null, [Validators.required]],
  });

  constructor(protected dCompanyService: DCompanyService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dCompany }) => {
      this.updateForm(dCompany);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dCompany = this.createFromForm();
    if (dCompany.id !== undefined) {
      this.subscribeToSaveResponse(this.dCompanyService.update(dCompany));
    } else {
      this.subscribeToSaveResponse(this.dCompanyService.create(dCompany));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDCompany>>): void {
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

  protected updateForm(dCompany: IDCompany): void {
    this.editForm.patchValue({
      company: dCompany.company,
      linkman: dCompany.linkman,
      phone: dCompany.phone,
      address: dCompany.address,
      remark: dCompany.remark,
      fax: dCompany.fax,
      id: dCompany.id,
    });
  }

  protected createFromForm(): IDCompany {
    return {
      ...new DCompany(),
      company: this.editForm.get(['company'])!.value,
      linkman: this.editForm.get(['linkman'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      address: this.editForm.get(['address'])!.value,
      remark: this.editForm.get(['remark'])!.value,
      fax: this.editForm.get(['fax'])!.value,
      id: this.editForm.get(['id'])!.value,
    };
  }
}
