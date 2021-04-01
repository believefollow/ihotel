import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDDb, getDDbIdentifier } from '../d-db.model';

export type EntityResponseType = HttpResponse<IDDb>;
export type EntityArrayResponseType = HttpResponse<IDDb[]>;

@Injectable({ providedIn: 'root' })
export class DDbService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/d-dbs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/d-dbs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dDb: IDDb): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dDb);
    return this.http
      .post<IDDb>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dDb: IDDb): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dDb);
    return this.http
      .put<IDDb>(`${this.resourceUrl}/${getDDbIdentifier(dDb) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dDb: IDDb): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dDb);
    return this.http
      .patch<IDDb>(`${this.resourceUrl}/${getDDbIdentifier(dDb) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDDb>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDDb[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDDb[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addDDbToCollectionIfMissing(dDbCollection: IDDb[], ...dDbsToCheck: (IDDb | null | undefined)[]): IDDb[] {
    const dDbs: IDDb[] = dDbsToCheck.filter(isPresent);
    if (dDbs.length > 0) {
      const dDbCollectionIdentifiers = dDbCollection.map(dDbItem => getDDbIdentifier(dDbItem)!);
      const dDbsToAdd = dDbs.filter(dDbItem => {
        const dDbIdentifier = getDDbIdentifier(dDbItem);
        if (dDbIdentifier == null || dDbCollectionIdentifiers.includes(dDbIdentifier)) {
          return false;
        }
        dDbCollectionIdentifiers.push(dDbIdentifier);
        return true;
      });
      return [...dDbsToAdd, ...dDbCollection];
    }
    return dDbCollection;
  }

  protected convertDateFromClient(dDb: IDDb): IDDb {
    return Object.assign({}, dDb, {
      dbdate: dDb.dbdate?.isValid() ? dDb.dbdate.toJSON() : undefined,
      lrdate: dDb.lrdate?.isValid() ? dDb.lrdate.toJSON() : undefined,
      f1sj: dDb.f1sj?.isValid() ? dDb.f1sj.toJSON() : undefined,
      f2sj: dDb.f2sj?.isValid() ? dDb.f2sj.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dbdate = res.body.dbdate ? dayjs(res.body.dbdate) : undefined;
      res.body.lrdate = res.body.lrdate ? dayjs(res.body.lrdate) : undefined;
      res.body.f1sj = res.body.f1sj ? dayjs(res.body.f1sj) : undefined;
      res.body.f2sj = res.body.f2sj ? dayjs(res.body.f2sj) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dDb: IDDb) => {
        dDb.dbdate = dDb.dbdate ? dayjs(dDb.dbdate) : undefined;
        dDb.lrdate = dDb.lrdate ? dayjs(dDb.lrdate) : undefined;
        dDb.f1sj = dDb.f1sj ? dayjs(dDb.f1sj) : undefined;
        dDb.f2sj = dDb.f2sj ? dayjs(dDb.f2sj) : undefined;
      });
    }
    return res;
  }
}
