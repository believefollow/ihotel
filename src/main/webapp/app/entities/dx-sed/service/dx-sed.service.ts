import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDxSed, getDxSedIdentifier } from '../dx-sed.model';

export type EntityResponseType = HttpResponse<IDxSed>;
export type EntityArrayResponseType = HttpResponse<IDxSed[]>;

@Injectable({ providedIn: 'root' })
export class DxSedService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/dx-seds');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/dx-seds');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dxSed: IDxSed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dxSed);
    return this.http
      .post<IDxSed>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dxSed: IDxSed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dxSed);
    return this.http
      .put<IDxSed>(`${this.resourceUrl}/${getDxSedIdentifier(dxSed) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dxSed: IDxSed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dxSed);
    return this.http
      .patch<IDxSed>(`${this.resourceUrl}/${getDxSedIdentifier(dxSed) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDxSed>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDxSed[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDxSed[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addDxSedToCollectionIfMissing(dxSedCollection: IDxSed[], ...dxSedsToCheck: (IDxSed | null | undefined)[]): IDxSed[] {
    const dxSeds: IDxSed[] = dxSedsToCheck.filter(isPresent);
    if (dxSeds.length > 0) {
      const dxSedCollectionIdentifiers = dxSedCollection.map(dxSedItem => getDxSedIdentifier(dxSedItem)!);
      const dxSedsToAdd = dxSeds.filter(dxSedItem => {
        const dxSedIdentifier = getDxSedIdentifier(dxSedItem);
        if (dxSedIdentifier == null || dxSedCollectionIdentifiers.includes(dxSedIdentifier)) {
          return false;
        }
        dxSedCollectionIdentifiers.push(dxSedIdentifier);
        return true;
      });
      return [...dxSedsToAdd, ...dxSedCollection];
    }
    return dxSedCollection;
  }

  protected convertDateFromClient(dxSed: IDxSed): IDxSed {
    return Object.assign({}, dxSed, {
      dxRq: dxSed.dxRq?.isValid() ? dxSed.dxRq.toJSON() : undefined,
      fsSj: dxSed.fsSj?.isValid() ? dxSed.fsSj.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dxRq = res.body.dxRq ? dayjs(res.body.dxRq) : undefined;
      res.body.fsSj = res.body.fsSj ? dayjs(res.body.fsSj) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dxSed: IDxSed) => {
        dxSed.dxRq = dxSed.dxRq ? dayjs(dxSed.dxRq) : undefined;
        dxSed.fsSj = dxSed.fsSj ? dayjs(dxSed.fsSj) : undefined;
      });
    }
    return res;
  }
}
