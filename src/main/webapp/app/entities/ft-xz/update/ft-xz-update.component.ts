import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IFtXz, FtXz } from '../ft-xz.model';
import { FtXzService } from '../service/ft-xz.service';

@Component({
  selector: 'jhi-ft-xz-update',
  templateUrl: './ft-xz-update.component.html',
})
export class FtXzUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    roomn: [null, [Validators.maxLength(50)]],
  });

  constructor(protected ftXzService: FtXzService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ftXz }) => {
      this.updateForm(ftXz);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ftXz = this.createFromForm();
    if (ftXz.id !== undefined) {
      this.subscribeToSaveResponse(this.ftXzService.update(ftXz));
    } else {
      this.subscribeToSaveResponse(this.ftXzService.create(ftXz));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFtXz>>): void {
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

  protected updateForm(ftXz: IFtXz): void {
    this.editForm.patchValue({
      id: ftXz.id,
      roomn: ftXz.roomn,
    });
  }

  protected createFromForm(): IFtXz {
    return {
      ...new FtXz(),
      id: this.editForm.get(['id'])!.value,
      roomn: this.editForm.get(['roomn'])!.value,
    };
  }
}
