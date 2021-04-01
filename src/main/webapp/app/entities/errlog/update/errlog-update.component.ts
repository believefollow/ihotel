import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IErrlog, Errlog } from '../errlog.model';
import { ErrlogService } from '../service/errlog.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-errlog-update',
  templateUrl: './errlog-update.component.html',
})
export class ErrlogUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    iderrlog: [null, [Validators.required]],
    errnumber: [],
    errtext: [null, [Validators.maxLength(255)]],
    errwindowmenu: [null, [Validators.maxLength(255)]],
    errobject: [null, [Validators.maxLength(255)]],
    errevent: [null, [Validators.maxLength(255)]],
    errline: [],
    errtime: [],
    sumbitsign: [],
    bmpfile: [null, [Validators.maxLength(255)]],
    bmpblob: [],
    bmpblobContentType: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected errlogService: ErrlogService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ errlog }) => {
      if (errlog.id === undefined) {
        const today = dayjs().startOf('day');
        errlog.errtime = today;
      }

      this.updateForm(errlog);
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('ihotelApp.error', { ...err, key: 'error.file.' + err.key })
        ),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const errlog = this.createFromForm();
    if (errlog.id !== undefined) {
      this.subscribeToSaveResponse(this.errlogService.update(errlog));
    } else {
      this.subscribeToSaveResponse(this.errlogService.create(errlog));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IErrlog>>): void {
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

  protected updateForm(errlog: IErrlog): void {
    this.editForm.patchValue({
      id: errlog.id,
      iderrlog: errlog.iderrlog,
      errnumber: errlog.errnumber,
      errtext: errlog.errtext,
      errwindowmenu: errlog.errwindowmenu,
      errobject: errlog.errobject,
      errevent: errlog.errevent,
      errline: errlog.errline,
      errtime: errlog.errtime ? errlog.errtime.format(DATE_TIME_FORMAT) : null,
      sumbitsign: errlog.sumbitsign,
      bmpfile: errlog.bmpfile,
      bmpblob: errlog.bmpblob,
      bmpblobContentType: errlog.bmpblobContentType,
    });
  }

  protected createFromForm(): IErrlog {
    return {
      ...new Errlog(),
      id: this.editForm.get(['id'])!.value,
      iderrlog: this.editForm.get(['iderrlog'])!.value,
      errnumber: this.editForm.get(['errnumber'])!.value,
      errtext: this.editForm.get(['errtext'])!.value,
      errwindowmenu: this.editForm.get(['errwindowmenu'])!.value,
      errobject: this.editForm.get(['errobject'])!.value,
      errevent: this.editForm.get(['errevent'])!.value,
      errline: this.editForm.get(['errline'])!.value,
      errtime: this.editForm.get(['errtime'])!.value ? dayjs(this.editForm.get(['errtime'])!.value, DATE_TIME_FORMAT) : undefined,
      sumbitsign: this.editForm.get(['sumbitsign'])!.value,
      bmpfile: this.editForm.get(['bmpfile'])!.value,
      bmpblobContentType: this.editForm.get(['bmpblobContentType'])!.value,
      bmpblob: this.editForm.get(['bmpblob'])!.value,
    };
  }
}
