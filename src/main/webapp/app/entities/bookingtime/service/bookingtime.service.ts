import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IBookingtime, getBookingtimeIdentifier } from '../bookingtime.model';

export type EntityResponseType = HttpResponse<IBookingtime>;
export type EntityArrayResponseType = HttpResponse<IBookingtime[]>;

@Injectable({ providedIn: 'root' })
export class BookingtimeService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/bookingtimes');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/bookingtimes');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(bookingtime: IBookingtime): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bookingtime);
    return this.http
      .post<IBookingtime>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bookingtime: IBookingtime): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bookingtime);
    return this.http
      .put<IBookingtime>(`${this.resourceUrl}/${getBookingtimeIdentifier(bookingtime) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(bookingtime: IBookingtime): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bookingtime);
    return this.http
      .patch<IBookingtime>(`${this.resourceUrl}/${getBookingtimeIdentifier(bookingtime) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBookingtime>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBookingtime[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBookingtime[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addBookingtimeToCollectionIfMissing(
    bookingtimeCollection: IBookingtime[],
    ...bookingtimesToCheck: (IBookingtime | null | undefined)[]
  ): IBookingtime[] {
    const bookingtimes: IBookingtime[] = bookingtimesToCheck.filter(isPresent);
    if (bookingtimes.length > 0) {
      const bookingtimeCollectionIdentifiers = bookingtimeCollection.map(bookingtimeItem => getBookingtimeIdentifier(bookingtimeItem)!);
      const bookingtimesToAdd = bookingtimes.filter(bookingtimeItem => {
        const bookingtimeIdentifier = getBookingtimeIdentifier(bookingtimeItem);
        if (bookingtimeIdentifier == null || bookingtimeCollectionIdentifiers.includes(bookingtimeIdentifier)) {
          return false;
        }
        bookingtimeCollectionIdentifiers.push(bookingtimeIdentifier);
        return true;
      });
      return [...bookingtimesToAdd, ...bookingtimeCollection];
    }
    return bookingtimeCollection;
  }

  protected convertDateFromClient(bookingtime: IBookingtime): IBookingtime {
    return Object.assign({}, bookingtime, {
      booktime: bookingtime.booktime?.isValid() ? bookingtime.booktime.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.booktime = res.body.booktime ? dayjs(res.body.booktime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((bookingtime: IBookingtime) => {
        bookingtime.booktime = bookingtime.booktime ? dayjs(bookingtime.booktime) : undefined;
      });
    }
    return res;
  }
}
