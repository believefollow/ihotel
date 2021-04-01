import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICyRoomtype, CyRoomtype } from '../cy-roomtype.model';
import { CyRoomtypeService } from '../service/cy-roomtype.service';

@Component({
  selector: 'jhi-cy-roomtype-update',
  templateUrl: './cy-roomtype-update.component.html',
})
export class CyRoomtypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    rtdm: [null, [Validators.required, Validators.maxLength(10)]],
    minc: [],
    servicerate: [],
    printer: [null, [Validators.maxLength(100)]],
    printnum: [],
  });

  constructor(protected cyRoomtypeService: CyRoomtypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cyRoomtype }) => {
      this.updateForm(cyRoomtype);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cyRoomtype = this.createFromForm();
    if (cyRoomtype.id !== undefined) {
      this.subscribeToSaveResponse(this.cyRoomtypeService.update(cyRoomtype));
    } else {
      this.subscribeToSaveResponse(this.cyRoomtypeService.create(cyRoomtype));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICyRoomtype>>): void {
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

  protected updateForm(cyRoomtype: ICyRoomtype): void {
    this.editForm.patchValue({
      id: cyRoomtype.id,
      rtdm: cyRoomtype.rtdm,
      minc: cyRoomtype.minc,
      servicerate: cyRoomtype.servicerate,
      printer: cyRoomtype.printer,
      printnum: cyRoomtype.printnum,
    });
  }

  protected createFromForm(): ICyRoomtype {
    return {
      ...new CyRoomtype(),
      id: this.editForm.get(['id'])!.value,
      rtdm: this.editForm.get(['rtdm'])!.value,
      minc: this.editForm.get(['minc'])!.value,
      servicerate: this.editForm.get(['servicerate'])!.value,
      printer: this.editForm.get(['printer'])!.value,
      printnum: this.editForm.get(['printnum'])!.value,
    };
  }
}
