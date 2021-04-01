import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDDepot, DDepot } from '../d-depot.model';
import { DDepotService } from '../service/d-depot.service';

@Component({
  selector: 'jhi-d-depot-update',
  templateUrl: './d-depot-update.component.html',
})
export class DDepotUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    depotid: [null, [Validators.required]],
    depot: [null, [Validators.required, Validators.maxLength(20)]],
  });

  constructor(protected dDepotService: DDepotService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dDepot }) => {
      this.updateForm(dDepot);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dDepot = this.createFromForm();
    if (dDepot.id !== undefined) {
      this.subscribeToSaveResponse(this.dDepotService.update(dDepot));
    } else {
      this.subscribeToSaveResponse(this.dDepotService.create(dDepot));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDDepot>>): void {
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

  protected updateForm(dDepot: IDDepot): void {
    this.editForm.patchValue({
      id: dDepot.id,
      depotid: dDepot.depotid,
      depot: dDepot.depot,
    });
  }

  protected createFromForm(): IDDepot {
    return {
      ...new DDepot(),
      id: this.editForm.get(['id'])!.value,
      depotid: this.editForm.get(['depotid'])!.value,
      depot: this.editForm.get(['depot'])!.value,
    };
  }
}
