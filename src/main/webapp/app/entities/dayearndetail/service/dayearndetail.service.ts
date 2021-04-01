import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDayearndetail, getDayearndetailIdentifier } from '../dayearndetail.model';

export type EntityResponseType = HttpResponse<IDayearndetail>;
export type EntityArrayResponseType = HttpResponse<IDayearndetail[]>;

@Injectable({ providedIn: 'root' })
export class DayearndetailService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/dayearndetails');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/dayearndetails');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dayearndetail: IDayearndetail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dayearndetail);
    return this.http
      .post<IDayearndetail>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dayearndetail: IDayearndetail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dayearndetail);
    return this.http
      .put<IDayearndetail>(`${this.resourceUrl}/${getDayearndetailIdentifier(dayearndetail) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dayearndetail: IDayearndetail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dayearndetail);
    return this.http
      .patch<IDayearndetail>(`${this.resourceUrl}/${getDayearndetailIdentifier(dayearndetail) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDayearndetail>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDayearndetail[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDayearndetail[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addDayearndetailToCollectionIfMissing(
    dayearndetailCollection: IDayearndetail[],
    ...dayearndetailsToCheck: (IDayearndetail | null | undefined)[]
  ): IDayearndetail[] {
    const dayearndetails: IDayearndetail[] = dayearndetailsToCheck.filter(isPresent);
    if (dayearndetails.length > 0) {
      const dayearndetailCollectionIdentifiers = dayearndetailCollection.map(
        dayearndetailItem => getDayearndetailIdentifier(dayearndetailItem)!
      );
      const dayearndetailsToAdd = dayearndetails.filter(dayearndetailItem => {
        const dayearndetailIdentifier = getDayearndetailIdentifier(dayearndetailItem);
        if (dayearndetailIdentifier == null || dayearndetailCollectionIdentifiers.includes(dayearndetailIdentifier)) {
          return false;
        }
        dayearndetailCollectionIdentifiers.push(dayearndetailIdentifier);
        return true;
      });
      return [...dayearndetailsToAdd, ...dayearndetailCollection];
    }
    return dayearndetailCollection;
  }

  protected convertDateFromClient(dayearndetail: IDayearndetail): IDayearndetail {
    return Object.assign({}, dayearndetail, {
      earndate: dayearndetail.earndate?.isValid() ? dayearndetail.earndate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.earndate = res.body.earndate ? dayjs(res.body.earndate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dayearndetail: IDayearndetail) => {
        dayearndetail.earndate = dayearndetail.earndate ? dayjs(dayearndetail.earndate) : undefined;
      });
    }
    return res;
  }
}
