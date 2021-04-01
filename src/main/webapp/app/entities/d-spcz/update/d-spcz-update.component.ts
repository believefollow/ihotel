import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDSpcz, DSpcz } from '../d-spcz.model';
import { DSpczService } from '../service/d-spcz.service';

@Component({
  selector: 'jhi-d-spcz-update',
  templateUrl: './d-spcz-update.component.html',
})
export class DSpczUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    rq: [],
    czrq: [],
  });

  constructor(protected dSpczService: DSpczService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dSpcz }) => {
      if (dSpcz.id === undefined) {
        const today = dayjs().startOf('day');
        dSpcz.rq = today;
        dSpcz.czrq = today;
      }

      this.updateForm(dSpcz);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dSpcz = this.createFromForm();
    if (dSpcz.id !== undefined) {
      this.subscribeToSaveResponse(this.dSpczService.update(dSpcz));
    } else {
      this.subscribeToSaveResponse(this.dSpczService.create(dSpcz));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDSpcz>>): void {
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

  protected updateForm(dSpcz: IDSpcz): void {
    this.editForm.patchValue({
      id: dSpcz.id,
      rq: dSpcz.rq ? dSpcz.rq.format(DATE_TIME_FORMAT) : null,
      czrq: dSpcz.czrq ? dSpcz.czrq.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IDSpcz {
    return {
      ...new DSpcz(),
      id: this.editForm.get(['id'])!.value,
      rq: this.editForm.get(['rq'])!.value ? dayjs(this.editForm.get(['rq'])!.value, DATE_TIME_FORMAT) : undefined,
      czrq: this.editForm.get(['czrq'])!.value ? dayjs(this.editForm.get(['czrq'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
