import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDXs, getDXsIdentifier } from '../d-xs.model';

export type EntityResponseType = HttpResponse<IDXs>;
export type EntityArrayResponseType = HttpResponse<IDXs[]>;

@Injectable({ providedIn: 'root' })
export class DXsService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/d-xs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/d-xs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dXs: IDXs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dXs);
    return this.http
      .post<IDXs>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dXs: IDXs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dXs);
    return this.http
      .put<IDXs>(`${this.resourceUrl}/${getDXsIdentifier(dXs) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dXs: IDXs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dXs);
    return this.http
      .patch<IDXs>(`${this.resourceUrl}/${getDXsIdentifier(dXs) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDXs>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDXs[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDXs[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addDXsToCollectionIfMissing(dXsCollection: IDXs[], ...dXsToCheck: (IDXs | null | undefined)[]): IDXs[] {
    const dXs: IDXs[] = dXsToCheck.filter(isPresent);
    if (dXs.length > 0) {
      const dXsCollectionIdentifiers = dXsCollection.map(dXsItem => getDXsIdentifier(dXsItem)!);
      const dXsToAdd = dXs.filter(dXsItem => {
        const dXsIdentifier = getDXsIdentifier(dXsItem);
        if (dXsIdentifier == null || dXsCollectionIdentifiers.includes(dXsIdentifier)) {
          return false;
        }
        dXsCollectionIdentifiers.push(dXsIdentifier);
        return true;
      });
      return [...dXsToAdd, ...dXsCollection];
    }
    return dXsCollection;
  }

  protected convertDateFromClient(dXs: IDXs): IDXs {
    return Object.assign({}, dXs, {
      begintime: dXs.begintime?.isValid() ? dXs.begintime.toJSON() : undefined,
      endtime: dXs.endtime?.isValid() ? dXs.endtime.toJSON() : undefined,
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
      res.body.forEach((dXs: IDXs) => {
        dXs.begintime = dXs.begintime ? dayjs(dXs.begintime) : undefined;
        dXs.endtime = dXs.endtime ? dayjs(dXs.endtime) : undefined;
      });
    }
    return res;
  }
}
