import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IClassreportRoom, ClassreportRoom } from '../classreport-room.model';
import { ClassreportRoomService } from '../service/classreport-room.service';

@Component({
  selector: 'jhi-classreport-room-update',
  templateUrl: './classreport-room-update.component.html',
})
export class ClassreportRoomUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    account: [null, [Validators.required, Validators.maxLength(30)]],
    roomn: [null, [Validators.maxLength(10)]],
    yfj: [],
    yfj9008: [],
    yfj9009: [],
    yfj9007: [],
    gz: [],
    ff: [],
    minibar: [],
    phone: [],
    other: [],
    pc: [],
    cz: [],
    cy: [],
    md: [],
    huiy: [],
    dtb: [],
    sszx: [],
  });

  constructor(
    protected classreportRoomService: ClassreportRoomService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classreportRoom }) => {
      this.updateForm(classreportRoom);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const classreportRoom = this.createFromForm();
    if (classreportRoom.id !== undefined) {
      this.subscribeToSaveResponse(this.classreportRoomService.update(classreportRoom));
    } else {
      this.subscribeToSaveResponse(this.classreportRoomService.create(classreportRoom));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassreportRoom>>): void {
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

  protected updateForm(classreportRoom: IClassreportRoom): void {
    this.editForm.patchValue({
      id: classreportRoom.id,
      account: classreportRoom.account,
      roomn: classreportRoom.roomn,
      yfj: classreportRoom.yfj,
      yfj9008: classreportRoom.yfj9008,
      yfj9009: classreportRoom.yfj9009,
      yfj9007: classreportRoom.yfj9007,
      gz: classreportRoom.gz,
      ff: classreportRoom.ff,
      minibar: classreportRoom.minibar,
      phone: classreportRoom.phone,
      other: classreportRoom.other,
      pc: classreportRoom.pc,
      cz: classreportRoom.cz,
      cy: classreportRoom.cy,
      md: classreportRoom.md,
      huiy: classreportRoom.huiy,
      dtb: classreportRoom.dtb,
      sszx: classreportRoom.sszx,
    });
  }

  protected createFromForm(): IClassreportRoom {
    return {
      ...new ClassreportRoom(),
      id: this.editForm.get(['id'])!.value,
      account: this.editForm.get(['account'])!.value,
      roomn: this.editForm.get(['roomn'])!.value,
      yfj: this.editForm.get(['yfj'])!.value,
      yfj9008: this.editForm.get(['yfj9008'])!.value,
      yfj9009: this.editForm.get(['yfj9009'])!.value,
      yfj9007: this.editForm.get(['yfj9007'])!.value,
      gz: this.editForm.get(['gz'])!.value,
      ff: this.editForm.get(['ff'])!.value,
      minibar: this.editForm.get(['minibar'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      other: this.editForm.get(['other'])!.value,
      pc: this.editForm.get(['pc'])!.value,
      cz: this.editForm.get(['cz'])!.value,
      cy: this.editForm.get(['cy'])!.value,
      md: this.editForm.get(['md'])!.value,
      huiy: this.editForm.get(['huiy'])!.value,
      dtb: this.editForm.get(['dtb'])!.value,
      sszx: this.editForm.get(['sszx'])!.value,
    };
  }
}
