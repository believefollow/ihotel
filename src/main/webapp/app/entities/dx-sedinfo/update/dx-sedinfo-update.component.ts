import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDxSedinfo, DxSedinfo } from '../dx-sedinfo.model';
import { DxSedinfoService } from '../service/dx-sedinfo.service';

@Component({
  selector: 'jhi-dx-sedinfo-update',
  templateUrl: './dx-sedinfo-update.component.html',
})
export class DxSedinfoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    yddx: [null, [Validators.maxLength(2)]],
    yddxmemo: [null, [Validators.maxLength(50)]],
    qxyddx: [null, [Validators.maxLength(2)]],
    qxydmemo: [null, [Validators.maxLength(50)]],
    czdx: [null, [Validators.maxLength(2)]],
    czmemo: [null, [Validators.maxLength(300)]],
    qxczdx: [null, [Validators.maxLength(10)]],
    qxczmemo: [null, [Validators.maxLength(300)]],
    yyedx: [null, [Validators.maxLength(2)]],
    yyememo: [null, [Validators.maxLength(300)]],
    fstime: [null, [Validators.maxLength(20)]],
    sffshm: [null, [Validators.maxLength(2)]],
    rzdx: [null, [Validators.maxLength(50)]],
    rzdxroomn: [null, [Validators.maxLength(50)]],
    jfdz: [null, [Validators.maxLength(50)]],
    blhy: [null, [Validators.maxLength(2)]],
    rzmemo: [null, [Validators.maxLength(300)]],
    blhymemo: [null, [Validators.maxLength(300)]],
    tfdx: [null, [Validators.maxLength(2)]],
    tfdxmemo: [null, [Validators.maxLength(300)]],
    fslb: [null, [Validators.maxLength(2)]],
    fslbmemo: [null, [Validators.maxLength(300)]],
  });

  constructor(protected dxSedinfoService: DxSedinfoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dxSedinfo }) => {
      this.updateForm(dxSedinfo);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dxSedinfo = this.createFromForm();
    if (dxSedinfo.id !== undefined) {
      this.subscribeToSaveResponse(this.dxSedinfoService.update(dxSedinfo));
    } else {
      this.subscribeToSaveResponse(this.dxSedinfoService.create(dxSedinfo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDxSedinfo>>): void {
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

  protected updateForm(dxSedinfo: IDxSedinfo): void {
    this.editForm.patchValue({
      id: dxSedinfo.id,
      yddx: dxSedinfo.yddx,
      yddxmemo: dxSedinfo.yddxmemo,
      qxyddx: dxSedinfo.qxyddx,
      qxydmemo: dxSedinfo.qxydmemo,
      czdx: dxSedinfo.czdx,
      czmemo: dxSedinfo.czmemo,
      qxczdx: dxSedinfo.qxczdx,
      qxczmemo: dxSedinfo.qxczmemo,
      yyedx: dxSedinfo.yyedx,
      yyememo: dxSedinfo.yyememo,
      fstime: dxSedinfo.fstime,
      sffshm: dxSedinfo.sffshm,
      rzdx: dxSedinfo.rzdx,
      rzdxroomn: dxSedinfo.rzdxroomn,
      jfdz: dxSedinfo.jfdz,
      blhy: dxSedinfo.blhy,
      rzmemo: dxSedinfo.rzmemo,
      blhymemo: dxSedinfo.blhymemo,
      tfdx: dxSedinfo.tfdx,
      tfdxmemo: dxSedinfo.tfdxmemo,
      fslb: dxSedinfo.fslb,
      fslbmemo: dxSedinfo.fslbmemo,
    });
  }

  protected createFromForm(): IDxSedinfo {
    return {
      ...new DxSedinfo(),
      id: this.editForm.get(['id'])!.value,
      yddx: this.editForm.get(['yddx'])!.value,
      yddxmemo: this.editForm.get(['yddxmemo'])!.value,
      qxyddx: this.editForm.get(['qxyddx'])!.value,
      qxydmemo: this.editForm.get(['qxydmemo'])!.value,
      czdx: this.editForm.get(['czdx'])!.value,
      czmemo: this.editForm.get(['czmemo'])!.value,
      qxczdx: this.editForm.get(['qxczdx'])!.value,
      qxczmemo: this.editForm.get(['qxczmemo'])!.value,
      yyedx: this.editForm.get(['yyedx'])!.value,
      yyememo: this.editForm.get(['yyememo'])!.value,
      fstime: this.editForm.get(['fstime'])!.value,
      sffshm: this.editForm.get(['sffshm'])!.value,
      rzdx: this.editForm.get(['rzdx'])!.value,
      rzdxroomn: this.editForm.get(['rzdxroomn'])!.value,
      jfdz: this.editForm.get(['jfdz'])!.value,
      blhy: this.editForm.get(['blhy'])!.value,
      rzmemo: this.editForm.get(['rzmemo'])!.value,
      blhymemo: this.editForm.get(['blhymemo'])!.value,
      tfdx: this.editForm.get(['tfdx'])!.value,
      tfdxmemo: this.editForm.get(['tfdxmemo'])!.value,
      fslb: this.editForm.get(['fslb'])!.value,
      fslbmemo: this.editForm.get(['fslbmemo'])!.value,
    };
  }
}
