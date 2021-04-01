import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IFtXzs, FtXzs } from '../ft-xzs.model';
import { FtXzsService } from '../service/ft-xzs.service';

@Component({
  selector: 'jhi-ft-xzs-update',
  templateUrl: './ft-xzs-update.component.html',
})
export class FtXzsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    roomn: [null, [Validators.maxLength(50)]],
  });

  constructor(protected ftXzsService: FtXzsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ftXzs }) => {
      this.updateForm(ftXzs);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ftXzs = this.createFromForm();
    if (ftXzs.id !== undefined) {
      this.subscribeToSaveResponse(this.ftXzsService.update(ftXzs));
    } else {
      this.subscribeToSaveResponse(this.ftXzsService.create(ftXzs));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFtXzs>>): void {
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

  protected updateForm(ftXzs: IFtXzs): void {
    this.editForm.patchValue({
      id: ftXzs.id,
      roomn: ftXzs.roomn,
    });
  }

  protected createFromForm(): IFtXzs {
    return {
      ...new FtXzs(),
      id: this.editForm.get(['id'])!.value,
      roomn: this.editForm.get(['roomn'])!.value,
    };
  }
}
