import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IClassRename, ClassRename } from '../class-rename.model';
import { ClassRenameService } from '../service/class-rename.service';

@Injectable({ providedIn: 'root' })
export class ClassRenameRoutingResolveService implements Resolve<IClassRename> {
  constructor(protected service: ClassRenameService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClassRename> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((classRename: HttpResponse<ClassRename>) => {
          if (classRename.body) {
            return of(classRename.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ClassRename());
  }
}
