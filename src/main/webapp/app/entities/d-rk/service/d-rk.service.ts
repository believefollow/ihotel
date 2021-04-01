import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDRk, getDRkIdentifier } from '../d-rk.model';

export type EntityResponseType = HttpResponse<IDRk>;
export type EntityArrayResponseType = HttpResponse<IDRk[]>;

@Injectable({ providedIn: 'root' })
export class DRkService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/d-rks');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/d-rks');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dRk: IDRk): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dRk);
    return this.http
      .post<IDRk>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dRk: IDRk): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dRk);
    return this.http
      .put<IDRk>(`${this.resourceUrl}/${getDRkIdentifier(dRk) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dRk: IDRk): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dRk);
    return this.http
      .patch<IDRk>(`${this.resourceUrl}/${getDRkIdentifier(dRk) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDRk>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDRk[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDRk[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addDRkToCollectionIfMissing(dRkCollection: IDRk[], ...dRksToCheck: (IDRk | null | undefined)[]): IDRk[] {
    const dRks: IDRk[] = dRksToCheck.filter(isPresent);
    if (dRks.length > 0) {
      const dRkCollectionIdentifiers = dRkCollection.map(dRkItem => getDRkIdentifier(dRkItem)!);
      const dRksToAdd = dRks.filter(dRkItem => {
        const dRkIdentifier = getDRkIdentifier(dRkItem);
        if (dRkIdentifier == null || dRkCollectionIdentifiers.includes(dRkIdentifier)) {
          return false;
        }
        dRkCollectionIdentifiers.push(dRkIdentifier);
        return true;
      });
      return [...dRksToAdd, ...dRkCollection];
    }
    return dRkCollection;
  }

  protected convertDateFromClient(dRk: IDRk): IDRk {
    return Object.assign({}, dRk, {
      rkdate: dRk.rkdate?.isValid() ? dRk.rkdate.toJSON() : undefined,
      lrdate: dRk.lrdate?.isValid() ? dRk.lrdate.toJSON() : undefined,
      f1sj: dRk.f1sj?.isValid() ? dRk.f1sj.toJSON() : undefined,
      f2sj: dRk.f2sj?.isValid() ? dRk.f2sj.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.rkdate = res.body.rkdate ? dayjs(res.body.rkdate) : undefined;
      res.body.lrdate = res.body.lrdate ? dayjs(res.body.lrdate) : undefined;
      res.body.f1sj = res.body.f1sj ? dayjs(res.body.f1sj) : undefined;
      res.body.f2sj = res.body.f2sj ? dayjs(res.body.f2sj) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dRk: IDRk) => {
        dRk.rkdate = dRk.rkdate ? dayjs(dRk.rkdate) : undefined;
        dRk.lrdate = dRk.lrdate ? dayjs(dRk.lrdate) : undefined;
        dRk.f1sj = dRk.f1sj ? dayjs(dRk.f1sj) : undefined;
        dRk.f2sj = dRk.f2sj ? dayjs(dRk.f2sj) : undefined;
      });
    }
    return res;
  }
}
