import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICyRoomtype } from '../cy-roomtype.model';

@Component({
  selector: 'jhi-cy-roomtype-detail',
  templateUrl: './cy-roomtype-detail.component.html',
})
export class CyRoomtypeDetailComponent implements OnInit {
  cyRoomtype: ICyRoomtype | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cyRoomtype }) => {
      this.cyRoomtype = cyRoomtype;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
