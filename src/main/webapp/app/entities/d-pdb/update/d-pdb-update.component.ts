import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDPdb, DPdb } from '../d-pdb.model';
import { DPdbService } from '../service/d-pdb.service';

@Component({
  selector: 'jhi-d-pdb-update',
  templateUrl: './d-pdb-update.component.html',
})
export class DPdbUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    begindate: [],
    enddate: [],
    bm: [null, [Validators.maxLength(20)]],
    spmc: [null, [Validators.maxLength(50)]],
    qcsl: [],
    rksl: [],
    xssl: [],
    dbsl: [],
    qtck: [],
    jcsl: [],
    swsl: [],
    pyk: [],
    memo: [null, [Validators.maxLength(200)]],
    depot: [null, [Validators.maxLength(50)]],
    rkje: [],
    xsje: [],
    dbje: [],
    jcje: [],
    dp: [null, [Validators.maxLength(50)]],
    qcje: [],
    swje: [],
    qtje: [],
  });

  constructor(protected dPdbService: DPdbService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dPdb }) => {
      if (dPdb.id === undefined) {
        const today = dayjs().startOf('day');
        dPdb.begindate = today;
        dPdb.enddate = today;
      }

      this.updateForm(dPdb);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dPdb = this.createFromForm();
    if (dPdb.id !== undefined) {
      this.subscribeToSaveResponse(this.dPdbService.update(dPdb));
    } else {
      this.subscribeToSaveResponse(this.dPdbService.create(dPdb));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDPdb>>): void {
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

  protected updateForm(dPdb: IDPdb): void {
    this.editForm.patchValue({
      id: dPdb.id,
      begindate: dPdb.begindate ? dPdb.begindate.format(DATE_TIME_FORMAT) : null,
      enddate: dPdb.enddate ? dPdb.enddate.format(DATE_TIME_FORMAT) : null,
      bm: dPdb.bm,
      spmc: dPdb.spmc,
      qcsl: dPdb.qcsl,
      rksl: dPdb.rksl,
      xssl: dPdb.xssl,
      dbsl: dPdb.dbsl,
      qtck: dPdb.qtck,
      jcsl: dPdb.jcsl,
      swsl: dPdb.swsl,
      pyk: dPdb.pyk,
      memo: dPdb.memo,
      depot: dPdb.depot,
      rkje: dPdb.rkje,
      xsje: dPdb.xsje,
      dbje: dPdb.dbje,
      jcje: dPdb.jcje,
      dp: dPdb.dp,
      qcje: dPdb.qcje,
      swje: dPdb.swje,
      qtje: dPdb.qtje,
    });
  }

  protected createFromForm(): IDPdb {
    return {
      ...new DPdb(),
      id: this.editForm.get(['id'])!.value,
      begindate: this.editForm.get(['begindate'])!.value ? dayjs(this.editForm.get(['begindate'])!.value, DATE_TIME_FORMAT) : undefined,
      enddate: this.editForm.get(['enddate'])!.value ? dayjs(this.editForm.get(['enddate'])!.value, DATE_TIME_FORMAT) : undefined,
      bm: this.editForm.get(['bm'])!.value,
      spmc: this.editForm.get(['spmc'])!.value,
      qcsl: this.editForm.get(['qcsl'])!.value,
      rksl: this.editForm.get(['rksl'])!.value,
      xssl: this.editForm.get(['xssl'])!.value,
      dbsl: this.editForm.get(['dbsl'])!.value,
      qtck: this.editForm.get(['qtck'])!.value,
      jcsl: this.editForm.get(['jcsl'])!.value,
      swsl: this.editForm.get(['swsl'])!.value,
      pyk: this.editForm.get(['pyk'])!.value,
      memo: this.editForm.get(['memo'])!.value,
      depot: this.editForm.get(['depot'])!.value,
      rkje: this.editForm.get(['rkje'])!.value,
      xsje: this.editForm.get(['xsje'])!.value,
      dbje: this.editForm.get(['dbje'])!.value,
      jcje: this.editForm.get(['jcje'])!.value,
      dp: this.editForm.get(['dp'])!.value,
      qcje: this.editForm.get(['qcje'])!.value,
      swje: this.editForm.get(['swje'])!.value,
      qtje: this.editForm.get(['qtje'])!.value,
    };
  }
}
