import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICk2xsy, Ck2xsy } from '../ck-2-xsy.model';
import { Ck2xsyService } from '../service/ck-2-xsy.service';

@Component({
  selector: 'jhi-ck-2-xsy-update',
  templateUrl: './ck-2-xsy-update.component.html',
})
export class Ck2xsyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    rq: [null, [Validators.required]],
    cpbh: [null, [Validators.required, Validators.maxLength(12)]],
    sl: [null, [Validators.required]],
  });

  constructor(protected ck2xsyService: Ck2xsyService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ck2xsy }) => {
      if (ck2xsy.id === undefined) {
        const today = dayjs().startOf('day');
        ck2xsy.rq = today;
      }

      this.updateForm(ck2xsy);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ck2xsy = this.createFromForm();
    if (ck2xsy.id !== undefined) {
      this.subscribeToSaveResponse(this.ck2xsyService.update(ck2xsy));
    } else {
      this.subscribeToSaveResponse(this.ck2xsyService.create(ck2xsy));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICk2xsy>>): void {
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

  protected updateForm(ck2xsy: ICk2xsy): void {
    this.editForm.patchValue({
      id: ck2xsy.id,
      rq: ck2xsy.rq ? ck2xsy.rq.format(DATE_TIME_FORMAT) : null,
      cpbh: ck2xsy.cpbh,
      sl: ck2xsy.sl,
    });
  }

  protected createFromForm(): ICk2xsy {
    return {
      ...new Ck2xsy(),
      id: this.editForm.get(['id'])!.value,
      rq: this.editForm.get(['rq'])!.value ? dayjs(this.editForm.get(['rq'])!.value, DATE_TIME_FORMAT) : undefined,
      cpbh: this.editForm.get(['cpbh'])!.value,
      sl: this.editForm.get(['sl'])!.value,
    };
  }
}
