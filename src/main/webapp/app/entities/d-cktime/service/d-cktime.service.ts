import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDCktime, getDCktimeIdentifier } from '../d-cktime.model';

export type EntityResponseType = HttpResponse<IDCktime>;
export type EntityArrayResponseType = HttpResponse<IDCktime[]>;

@Injectable({ providedIn: 'root' })
export class DCktimeService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/d-cktimes');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/d-cktimes');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dCktime: IDCktime): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dCktime);
    return this.http
      .post<IDCktime>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dCktime: IDCktime): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dCktime);
    return this.http
      .put<IDCktime>(`${this.resourceUrl}/${getDCktimeIdentifier(dCktime) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dCktime: IDCktime): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dCktime);
    return this.http
      .patch<IDCktime>(`${this.resourceUrl}/${getDCktimeIdentifier(dCktime) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDCktime>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDCktime[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDCktime[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addDCktimeToCollectionIfMissing(dCktimeCollection: IDCktime[], ...dCktimesToCheck: (IDCktime | null | undefined)[]): IDCktime[] {
    const dCktimes: IDCktime[] = dCktimesToCheck.filter(isPresent);
    if (dCktimes.length > 0) {
      const dCktimeCollectionIdentifiers = dCktimeCollection.map(dCktimeItem => getDCktimeIdentifier(dCktimeItem)!);
      const dCktimesToAdd = dCktimes.filter(dCktimeItem => {
        const dCktimeIdentifier = getDCktimeIdentifier(dCktimeItem);
        if (dCktimeIdentifier == null || dCktimeCollectionIdentifiers.includes(dCktimeIdentifier)) {
          return false;
        }
        dCktimeCollectionIdentifiers.push(dCktimeIdentifier);
        return true;
      });
      return [...dCktimesToAdd, ...dCktimeCollection];
    }
    return dCktimeCollection;
  }

  protected convertDateFromClient(dCktime: IDCktime): IDCktime {
    return Object.assign({}, dCktime, {
      begintime: dCktime.begintime?.isValid() ? dCktime.begintime.toJSON() : undefined,
      endtime: dCktime.endtime?.isValid() ? dCktime.endtime.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.begintime = res.body.begintime ? dayjs(res.body.begintime) : undefined;
      res.body.endtime = res.body.endtime ? dayjs(res.body.endtime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dCktime: IDCktime) => {
        dCktime.begintime = dCktime.begintime ? dayjs(dCktime.begintime) : undefined;
        dCktime.endtime = dCktime.endtime ? dayjs(dCktime.endtime) : undefined;
      });
    }
    return res;
  }
}
