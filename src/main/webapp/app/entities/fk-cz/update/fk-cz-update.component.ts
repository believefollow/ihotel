import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IFkCz, FkCz } from '../fk-cz.model';
import { FkCzService } from '../service/fk-cz.service';

@Component({
  selector: 'jhi-fk-cz-update',
  templateUrl: './fk-cz-update.component.html',
})
export class FkCzUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    hoteltime: [null, [Validators.required]],
    wxf: [],
    ksf: [],
    kf: [],
    zfs: [],
    groupyd: [],
    skyd: [],
    ydwd: [],
    qxyd: [],
    isnew: [],
    hoteldm: [null, [Validators.maxLength(50)]],
    hys: [],
    khys: [],
  });

  constructor(protected fkCzService: FkCzService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fkCz }) => {
      if (fkCz.id === undefined) {
        const today = dayjs().startOf('day');
        fkCz.hoteltime = today;
      }

      this.updateForm(fkCz);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fkCz = this.createFromForm();
    if (fkCz.id !== undefined) {
      this.subscribeToSaveResponse(this.fkCzService.update(fkCz));
    } else {
      this.subscribeToSaveResponse(this.fkCzService.create(fkCz));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFkCz>>): void {
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

  protected updateForm(fkCz: IFkCz): void {
    this.editForm.patchValue({
      id: fkCz.id,
      hoteltime: fkCz.hoteltime ? fkCz.hoteltime.format(DATE_TIME_FORMAT) : null,
      wxf: fkCz.wxf,
      ksf: fkCz.ksf,
      kf: fkCz.kf,
      zfs: fkCz.zfs,
      groupyd: fkCz.groupyd,
      skyd: fkCz.skyd,
      ydwd: fkCz.ydwd,
      qxyd: fkCz.qxyd,
      isnew: fkCz.isnew,
      hoteldm: fkCz.hoteldm,
      hys: fkCz.hys,
      khys: fkCz.khys,
    });
  }

  protected createFromForm(): IFkCz {
    return {
      ...new FkCz(),
      id: this.editForm.get(['id'])!.value,
      hoteltime: this.editForm.get(['hoteltime'])!.value ? dayjs(this.editForm.get(['hoteltime'])!.value, DATE_TIME_FORMAT) : undefined,
      wxf: this.editForm.get(['wxf'])!.value,
      ksf: this.editForm.get(['ksf'])!.value,
      kf: this.editForm.get(['kf'])!.value,
      zfs: this.editForm.get(['zfs'])!.value,
      groupyd: this.editForm.get(['groupyd'])!.value,
      skyd: this.editForm.get(['skyd'])!.value,
      ydwd: this.editForm.get(['ydwd'])!.value,
      qxyd: this.editForm.get(['qxyd'])!.value,
      isnew: this.editForm.get(['isnew'])!.value,
      hoteldm: this.editForm.get(['hoteldm'])!.value,
      hys: this.editForm.get(['hys'])!.value,
      khys: this.editForm.get(['khys'])!.value,
    };
  }
}
