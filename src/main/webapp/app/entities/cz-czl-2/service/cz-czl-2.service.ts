import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICzCzl2, getCzCzl2Identifier } from '../cz-czl-2.model';

export type EntityResponseType = HttpResponse<ICzCzl2>;
export type EntityArrayResponseType = HttpResponse<ICzCzl2[]>;

@Injectable({ providedIn: 'root' })
export class CzCzl2Service {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/cz-czl-2-s');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/cz-czl-2-s');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(czCzl2: ICzCzl2): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(czCzl2);
    return this.http
      .post<ICzCzl2>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(czCzl2: ICzCzl2): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(czCzl2);
    return this.http
      .put<ICzCzl2>(`${this.resourceUrl}/${getCzCzl2Identifier(czCzl2) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(czCzl2: ICzCzl2): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(czCzl2);
    return this.http
      .patch<ICzCzl2>(`${this.resourceUrl}/${getCzCzl2Identifier(czCzl2) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICzCzl2>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICzCzl2[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICzCzl2[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addCzCzl2ToCollectionIfMissing(czCzl2Collection: ICzCzl2[], ...czCzl2sToCheck: (ICzCzl2 | null | undefined)[]): ICzCzl2[] {
    const czCzl2s: ICzCzl2[] = czCzl2sToCheck.filter(isPresent);
    if (czCzl2s.length > 0) {
      const czCzl2CollectionIdentifiers = czCzl2Collection.map(czCzl2Item => getCzCzl2Identifier(czCzl2Item)!);
      const czCzl2sToAdd = czCzl2s.filter(czCzl2Item => {
        const czCzl2Identifier = getCzCzl2Identifier(czCzl2Item);
        if (czCzl2Identifier == null || czCzl2CollectionIdentifiers.includes(czCzl2Identifier)) {
          return false;
        }
        czCzl2CollectionIdentifiers.push(czCzl2Identifier);
        return true;
      });
      return [...czCzl2sToAdd, ...czCzl2Collection];
    }
    return czCzl2Collection;
  }

  protected convertDateFromClient(czCzl2: ICzCzl2): ICzCzl2 {
    return Object.assign({}, czCzl2, {
      dr: czCzl2.dr?.isValid() ? czCzl2.dr.toJSON() : undefined,
      dqdate: czCzl2.dqdate?.isValid() ? czCzl2.dqdate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dr = res.body.dr ? dayjs(res.body.dr) : undefined;
      res.body.dqdate = res.body.dqdate ? dayjs(res.body.dqdate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((czCzl2: ICzCzl2) => {
        czCzl2.dr = czCzl2.dr ? dayjs(czCzl2.dr) : undefined;
        czCzl2.dqdate = czCzl2.dqdate ? dayjs(czCzl2.dqdate) : undefined;
      });
    }
    return res;
  }
}
