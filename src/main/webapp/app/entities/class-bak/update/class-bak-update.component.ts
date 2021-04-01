import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IClassBak, ClassBak } from '../class-bak.model';
import { ClassBakService } from '../service/class-bak.service';

@Component({
  selector: 'jhi-class-bak-update',
  templateUrl: './class-bak-update.component.html',
})
export class ClassBakUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    empn: [null, [Validators.maxLength(100)]],
    dt: [],
    rq: [],
    ghname: [null, [Validators.maxLength(100)]],
    bak: [null, [Validators.maxLength(100)]],
  });

  constructor(protected classBakService: ClassBakService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classBak }) => {
      if (classBak.id === undefined) {
        const today = dayjs().startOf('day');
        classBak.dt = today;
        classBak.rq = today;
      }

      this.updateForm(classBak);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const classBak = this.createFromForm();
    if (classBak.id !== undefined) {
      this.subscribeToSaveResponse(this.classBakService.update(classBak));
    } else {
      this.subscribeToSaveResponse(this.classBakService.create(classBak));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassBak>>): void {
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

  protected updateForm(classBak: IClassBak): void {
    this.editForm.patchValue({
      id: classBak.id,
      empn: classBak.empn,
      dt: classBak.dt ? classBak.dt.format(DATE_TIME_FORMAT) : null,
      rq: classBak.rq ? classBak.rq.format(DATE_TIME_FORMAT) : null,
      ghname: classBak.ghname,
      bak: classBak.bak,
    });
  }

  protected createFromForm(): IClassBak {
    return {
      ...new ClassBak(),
      id: this.editForm.get(['id'])!.value,
      empn: this.editForm.get(['empn'])!.value,
      dt: this.editForm.get(['dt'])!.value ? dayjs(this.editForm.get(['dt'])!.value, DATE_TIME_FORMAT) : undefined,
      rq: this.editForm.get(['rq'])!.value ? dayjs(this.editForm.get(['rq'])!.value, DATE_TIME_FORMAT) : undefined,
      ghname: this.editForm.get(['ghname'])!.value,
      bak: this.editForm.get(['bak'])!.value,
    };
  }
}
