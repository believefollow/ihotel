import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IClassreportRoom, ClassreportRoom } from '../classreport-room.model';
import { ClassreportRoomService } from '../service/classreport-room.service';

@Injectable({ providedIn: 'root' })
export class ClassreportRoomRoutingResolveService implements Resolve<IClassreportRoom> {
  constructor(protected service: ClassreportRoomService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClassreportRoom> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((classreportRoom: HttpResponse<ClassreportRoom>) => {
          if (classreportRoom.body) {
            return of(classreportRoom.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ClassreportRoom());
  }
}
