import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClassreportRoom } from '../classreport-room.model';

@Component({
  selector: 'jhi-classreport-room-detail',
  templateUrl: './classreport-room-detail.component.html',
})
export class ClassreportRoomDetailComponent implements OnInit {
  classreportRoom: IClassreportRoom | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classreportRoom }) => {
      this.classreportRoom = classreportRoom;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
