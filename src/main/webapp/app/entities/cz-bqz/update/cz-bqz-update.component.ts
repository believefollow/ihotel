import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICzBqz, CzBqz } from '../cz-bqz.model';
import { CzBqzService } from '../service/cz-bqz.service';

@Component({
  selector: 'jhi-cz-bqz-update',
  templateUrl: './cz-bqz-update.component.html',
})
export class CzBqzUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    rq: [],
    qSl: [],
    qKfl: [],
    qPjz: [],
    qYsfz: [],
    qSjfz: [],
    qFzcz: [],
    qPjzcz: [],
    bSl: [],
    bKfl: [],
    bPjz: [],
    bYsfz: [],
    bSjfz: [],
    bFzcz: [],
    bPjzcz: [],
    zSl: [],
    zKfl: [],
    zPjz: [],
    zYsfz: [],
    zSjfz: [],
    zFzcz: [],
    zPjzcz: [],
    zk: [],
  });

  constructor(protected czBqzService: CzBqzService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ czBqz }) => {
      if (czBqz.id === undefined) {
        const today = dayjs().startOf('day');
        czBqz.rq = today;
      }

      this.updateForm(czBqz);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const czBqz = this.createFromForm();
    if (czBqz.id !== undefined) {
      this.subscribeToSaveResponse(this.czBqzService.update(czBqz));
    } else {
      this.subscribeToSaveResponse(this.czBqzService.create(czBqz));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICzBqz>>): void {
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

  protected updateForm(czBqz: ICzBqz): void {
    this.editForm.patchValue({
      id: czBqz.id,
      rq: czBqz.rq ? czBqz.rq.format(DATE_TIME_FORMAT) : null,
      qSl: czBqz.qSl,
      qKfl: czBqz.qKfl,
      qPjz: czBqz.qPjz,
      qYsfz: czBqz.qYsfz,
      qSjfz: czBqz.qSjfz,
      qFzcz: czBqz.qFzcz,
      qPjzcz: czBqz.qPjzcz,
      bSl: czBqz.bSl,
      bKfl: czBqz.bKfl,
      bPjz: czBqz.bPjz,
      bYsfz: czBqz.bYsfz,
      bSjfz: czBqz.bSjfz,
      bFzcz: czBqz.bFzcz,
      bPjzcz: czBqz.bPjzcz,
      zSl: czBqz.zSl,
      zKfl: czBqz.zKfl,
      zPjz: czBqz.zPjz,
      zYsfz: czBqz.zYsfz,
      zSjfz: czBqz.zSjfz,
      zFzcz: czBqz.zFzcz,
      zPjzcz: czBqz.zPjzcz,
      zk: czBqz.zk,
    });
  }

  protected createFromForm(): ICzBqz {
    return {
      ...new CzBqz(),
      id: this.editForm.get(['id'])!.value,
      rq: this.editForm.get(['rq'])!.value ? dayjs(this.editForm.get(['rq'])!.value, DATE_TIME_FORMAT) : undefined,
      qSl: this.editForm.get(['qSl'])!.value,
      qKfl: this.editForm.get(['qKfl'])!.value,
      qPjz: this.editForm.get(['qPjz'])!.value,
      qYsfz: this.editForm.get(['qYsfz'])!.value,
      qSjfz: this.editForm.get(['qSjfz'])!.value,
      qFzcz: this.editForm.get(['qFzcz'])!.value,
      qPjzcz: this.editForm.get(['qPjzcz'])!.value,
      bSl: this.editForm.get(['bSl'])!.value,
      bKfl: this.editForm.get(['bKfl'])!.value,
      bPjz: this.editForm.get(['bPjz'])!.value,
      bYsfz: this.editForm.get(['bYsfz'])!.value,
      bSjfz: this.editForm.get(['bSjfz'])!.value,
      bFzcz: this.editForm.get(['bFzcz'])!.value,
      bPjzcz: this.editForm.get(['bPjzcz'])!.value,
      zSl: this.editForm.get(['zSl'])!.value,
      zKfl: this.editForm.get(['zKfl'])!.value,
      zPjz: this.editForm.get(['zPjz'])!.value,
      zYsfz: this.editForm.get(['zYsfz'])!.value,
      zSjfz: this.editForm.get(['zSjfz'])!.value,
      zFzcz: this.editForm.get(['zFzcz'])!.value,
      zPjzcz: this.editForm.get(['zPjzcz'])!.value,
      zk: this.editForm.get(['zk'])!.value,
    };
  }
}
