import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IBookYst, BookYst } from '../book-yst.model';
import { BookYstService } from '../service/book-yst.service';

@Component({
  selector: 'jhi-book-yst-update',
  templateUrl: './book-yst-update.component.html',
})
export class BookYstUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    roomcode: [null, [Validators.maxLength(100)]],
    roomname: [null, [Validators.required, Validators.maxLength(100)]],
    roomnum: [null, [Validators.maxLength(100)]],
    roomseparatenum: [null, [Validators.maxLength(100)]],
    bedids: [null, [Validators.maxLength(200)]],
    bedsimpledesc: [null, [Validators.maxLength(100)]],
    bednum: [null, [Validators.maxLength(200)]],
    roomsize: [null, [Validators.maxLength(200)]],
    roomfloor: [null, [Validators.maxLength(200)]],
    netservice: [null, [Validators.maxLength(200)]],
    nettype: [null, [Validators.maxLength(200)]],
    iswindow: [null, [Validators.maxLength(200)]],
    remark: [null, [Validators.maxLength(200)]],
    sortid: [null, [Validators.maxLength(200)]],
    roomstate: [null, [Validators.maxLength(200)]],
    source: [null, [Validators.maxLength(100)]],
    roomamenities: [null, [Validators.maxLength(100)]],
    maxguestnums: [null, [Validators.maxLength(200)]],
    roomdistribution: [null, [Validators.maxLength(100)]],
    conditionbeforedays: [null, [Validators.maxLength(100)]],
    conditionleastdays: [null, [Validators.maxLength(100)]],
    conditionleastroomnum: [null, [Validators.maxLength(100)]],
    paymentype: [null, [Validators.maxLength(100)]],
    rateplandesc: [null, [Validators.maxLength(200)]],
    rateplanname: [null, [Validators.maxLength(100)]],
    rateplanstate: [null, [Validators.maxLength(100)]],
    addvaluebednum: [null, [Validators.maxLength(100)]],
    addvaluebedprice: [null, [Validators.maxLength(200)]],
    addvaluebreakfastnum: [null, [Validators.maxLength(200)]],
    addvaluebreakfastprice: [null, [Validators.maxLength(200)]],
    baseprice: [null, [Validators.maxLength(200)]],
    saleprice: [null, [Validators.maxLength(200)]],
    marketprice: [null, [Validators.maxLength(200)]],
    hotelproductservice: [null, [Validators.maxLength(100)]],
    hotelproductservicedesc: [null, [Validators.maxLength(500)]],
    hotelproductid: [null, [Validators.maxLength(50)]],
    roomid: [null, [Validators.maxLength(50)]],
    hotelid: [null, [Validators.maxLength(50)]],
  });

  constructor(protected bookYstService: BookYstService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookYst }) => {
      this.updateForm(bookYst);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bookYst = this.createFromForm();
    if (bookYst.id !== undefined) {
      this.subscribeToSaveResponse(this.bookYstService.update(bookYst));
    } else {
      this.subscribeToSaveResponse(this.bookYstService.create(bookYst));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBookYst>>): void {
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

  protected updateForm(bookYst: IBookYst): void {
    this.editForm.patchValue({
      id: bookYst.id,
      roomcode: bookYst.roomcode,
      roomname: bookYst.roomname,
      roomnum: bookYst.roomnum,
      roomseparatenum: bookYst.roomseparatenum,
      bedids: bookYst.bedids,
      bedsimpledesc: bookYst.bedsimpledesc,
      bednum: bookYst.bednum,
      roomsize: bookYst.roomsize,
      roomfloor: bookYst.roomfloor,
      netservice: bookYst.netservice,
      nettype: bookYst.nettype,
      iswindow: bookYst.iswindow,
      remark: bookYst.remark,
      sortid: bookYst.sortid,
      roomstate: bookYst.roomstate,
      source: bookYst.source,
      roomamenities: bookYst.roomamenities,
      maxguestnums: bookYst.maxguestnums,
      roomdistribution: bookYst.roomdistribution,
      conditionbeforedays: bookYst.conditionbeforedays,
      conditionleastdays: bookYst.conditionleastdays,
      conditionleastroomnum: bookYst.conditionleastroomnum,
      paymentype: bookYst.paymentype,
      rateplandesc: bookYst.rateplandesc,
      rateplanname: bookYst.rateplanname,
      rateplanstate: bookYst.rateplanstate,
      addvaluebednum: bookYst.addvaluebednum,
      addvaluebedprice: bookYst.addvaluebedprice,
      addvaluebreakfastnum: bookYst.addvaluebreakfastnum,
      addvaluebreakfastprice: bookYst.addvaluebreakfastprice,
      baseprice: bookYst.baseprice,
      saleprice: bookYst.saleprice,
      marketprice: bookYst.marketprice,
      hotelproductservice: bookYst.hotelproductservice,
      hotelproductservicedesc: bookYst.hotelproductservicedesc,
      hotelproductid: bookYst.hotelproductid,
      roomid: bookYst.roomid,
      hotelid: bookYst.hotelid,
    });
  }

  protected createFromForm(): IBookYst {
    return {
      ...new BookYst(),
      id: this.editForm.get(['id'])!.value,
      roomcode: this.editForm.get(['roomcode'])!.value,
      roomname: this.editForm.get(['roomname'])!.value,
      roomnum: this.editForm.get(['roomnum'])!.value,
      roomseparatenum: this.editForm.get(['roomseparatenum'])!.value,
      bedids: this.editForm.get(['bedids'])!.value,
      bedsimpledesc: this.editForm.get(['bedsimpledesc'])!.value,
      bednum: this.editForm.get(['bednum'])!.value,
      roomsize: this.editForm.get(['roomsize'])!.value,
      roomfloor: this.editForm.get(['roomfloor'])!.value,
      netservice: this.editForm.get(['netservice'])!.value,
      nettype: this.editForm.get(['nettype'])!.value,
      iswindow: this.editForm.get(['iswindow'])!.value,
      remark: this.editForm.get(['remark'])!.value,
      sortid: this.editForm.get(['sortid'])!.value,
      roomstate: this.editForm.get(['roomstate'])!.value,
      source: this.editForm.get(['source'])!.value,
      roomamenities: this.editForm.get(['roomamenities'])!.value,
      maxguestnums: this.editForm.get(['maxguestnums'])!.value,
      roomdistribution: this.editForm.get(['roomdistribution'])!.value,
      conditionbeforedays: this.editForm.get(['conditionbeforedays'])!.value,
      conditionleastdays: this.editForm.get(['conditionleastdays'])!.value,
      conditionleastroomnum: this.editForm.get(['conditionleastroomnum'])!.value,
      paymentype: this.editForm.get(['paymentype'])!.value,
      rateplandesc: this.editForm.get(['rateplandesc'])!.value,
      rateplanname: this.editForm.get(['rateplanname'])!.value,
      rateplanstate: this.editForm.get(['rateplanstate'])!.value,
      addvaluebednum: this.editForm.get(['addvaluebednum'])!.value,
      addvaluebedprice: this.editForm.get(['addvaluebedprice'])!.value,
      addvaluebreakfastnum: this.editForm.get(['addvaluebreakfastnum'])!.value,
      addvaluebreakfastprice: this.editForm.get(['addvaluebreakfastprice'])!.value,
      baseprice: this.editForm.get(['baseprice'])!.value,
      saleprice: this.editForm.get(['saleprice'])!.value,
      marketprice: this.editForm.get(['marketprice'])!.value,
      hotelproductservice: this.editForm.get(['hotelproductservice'])!.value,
      hotelproductservicedesc: this.editForm.get(['hotelproductservicedesc'])!.value,
      hotelproductid: this.editForm.get(['hotelproductid'])!.value,
      roomid: this.editForm.get(['roomid'])!.value,
      hotelid: this.editForm.get(['hotelid'])!.value,
    };
  }
}
