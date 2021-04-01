import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDCk, getDCkIdentifier } from '../d-ck.model';

export type EntityResponseType = HttpResponse<IDCk>;
export type EntityArrayResponseType = HttpResponse<IDCk[]>;

@Injectable({ providedIn: 'root' })
export class DCkService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/d-cks');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/d-cks');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dCk: IDCk): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dCk);
    return this.http
      .post<IDCk>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dCk: IDCk): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dCk);
    return this.http
      .put<IDCk>(`${this.resourceUrl}/${getDCkIdentifier(dCk) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dCk: IDCk): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dCk);
    return this.http
      .patch<IDCk>(`${this.resourceUrl}/${getDCkIdentifier(dCk) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDCk>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDCk[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDCk[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addDCkToCollectionIfMissing(dCkCollection: IDCk[], ...dCksToCheck: (IDCk | null | undefined)[]): IDCk[] {
    const dCks: IDCk[] = dCksToCheck.filter(isPresent);
    if (dCks.length > 0) {
      const dCkCollectionIdentifiers = dCkCollection.map(dCkItem => getDCkIdentifier(dCkItem)!);
      const dCksToAdd = dCks.filter(dCkItem => {
        const dCkIdentifier = getDCkIdentifier(dCkItem);
        if (dCkIdentifier == null || dCkCollectionIdentifiers.includes(dCkIdentifier)) {
          return false;
        }
        dCkCollectionIdentifiers.push(dCkIdentifier);
        return true;
      });
      return [...dCksToAdd, ...dCkCollection];
    }
    return dCkCollection;
  }

  protected convertDateFromClient(dCk: IDCk): IDCk {
    return Object.assign({}, dCk, {
      ckdate: dCk.ckdate?.isValid() ? dCk.ckdate.toJSON() : undefined,
      lrdate: dCk.lrdate?.isValid() ? dCk.lrdate.toJSON() : undefined,
      f1sj: dCk.f1sj?.isValid() ? dCk.f1sj.toJSON() : undefined,
      f2sj: dCk.f2sj?.isValid() ? dCk.f2sj.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.ckdate = res.body.ckdate ? dayjs(res.body.ckdate) : undefined;
      res.body.lrdate = res.body.lrdate ? dayjs(res.body.lrdate) : undefined;
      res.body.f1sj = res.body.f1sj ? dayjs(res.body.f1sj) : undefined;
      res.body.f2sj = res.body.f2sj ? dayjs(res.body.f2sj) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dCk: IDCk) => {
        dCk.ckdate = dCk.ckdate ? dayjs(dCk.ckdate) : undefined;
        dCk.lrdate = dCk.lrdate ? dayjs(dCk.lrdate) : undefined;
        dCk.f1sj = dCk.f1sj ? dayjs(dCk.f1sj) : undefined;
        dCk.f2sj = dCk.f2sj ? dayjs(dCk.f2sj) : undefined;
      });
    }
    return res;
  }
}
