import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICyCptype, CyCptype } from '../cy-cptype.model';
import { CyCptypeService } from '../service/cy-cptype.service';

@Component({
  selector: 'jhi-cy-cptype-update',
  templateUrl: './cy-cptype-update.component.html',
})
export class CyCptypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    cptdm: [null, [Validators.required, Validators.maxLength(4)]],
    cptname: [null, [Validators.required, Validators.maxLength(20)]],
    printsign: [null, [Validators.required]],
    printer: [null, [Validators.maxLength(100)]],
    printnum: [],
    printcut: [],
    syssign: [],
    typesign: [null, [Validators.maxLength(100)]],
    qy: [null, [Validators.maxLength(20)]],
  });

  constructor(protected cyCptypeService: CyCptypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cyCptype }) => {
      this.updateForm(cyCptype);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cyCptype = this.createFromForm();
    if (cyCptype.id !== undefined) {
      this.subscribeToSaveResponse(this.cyCptypeService.update(cyCptype));
    } else {
      this.subscribeToSaveResponse(this.cyCptypeService.create(cyCptype));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICyCptype>>): void {
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

  protected updateForm(cyCptype: ICyCptype): void {
    this.editForm.patchValue({
      id: cyCptype.id,
      cptdm: cyCptype.cptdm,
      cptname: cyCptype.cptname,
      printsign: cyCptype.printsign,
      printer: cyCptype.printer,
      printnum: cyCptype.printnum,
      printcut: cyCptype.printcut,
      syssign: cyCptype.syssign,
      typesign: cyCptype.typesign,
      qy: cyCptype.qy,
    });
  }

  protected createFromForm(): ICyCptype {
    return {
      ...new CyCptype(),
      id: this.editForm.get(['id'])!.value,
      cptdm: this.editForm.get(['cptdm'])!.value,
      cptname: this.editForm.get(['cptname'])!.value,
      printsign: this.editForm.get(['printsign'])!.value,
      printer: this.editForm.get(['printer'])!.value,
      printnum: this.editForm.get(['printnum'])!.value,
      printcut: this.editForm.get(['printcut'])!.value,
      syssign: this.editForm.get(['syssign'])!.value,
      typesign: this.editForm.get(['typesign'])!.value,
      qy: this.editForm.get(['qy'])!.value,
    };
  }
}
