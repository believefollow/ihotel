import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBookYst, BookYst } from '../book-yst.model';
import { BookYstService } from '../service/book-yst.service';

@Injectable({ providedIn: 'root' })
export class BookYstRoutingResolveService implements Resolve<IBookYst> {
  constructor(protected service: BookYstService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBookYst> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bookYst: HttpResponse<BookYst>) => {
          if (bookYst.body) {
            return of(bookYst.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BookYst());
  }
}
