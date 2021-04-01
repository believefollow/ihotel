import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDSpcz, getDSpczIdentifier } from '../d-spcz.model';

export type EntityResponseType = HttpResponse<IDSpcz>;
export type EntityArrayResponseType = HttpResponse<IDSpcz[]>;

@Injectable({ providedIn: 'root' })
export class DSpczService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/d-spczs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/d-spczs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dSpcz: IDSpcz): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dSpcz);
    return this.http
      .post<IDSpcz>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dSpcz: IDSpcz): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dSpcz);
    return this.http
      .put<IDSpcz>(`${this.resourceUrl}/${getDSpczIdentifier(dSpcz) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dSpcz: IDSpcz): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dSpcz);
    return this.http
      .patch<IDSpcz>(`${this.resourceUrl}/${getDSpczIdentifier(dSpcz) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDSpcz>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDSpcz[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDSpcz[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addDSpczToCollectionIfMissing(dSpczCollection: IDSpcz[], ...dSpczsToCheck: (IDSpcz | null | undefined)[]): IDSpcz[] {
    const dSpczs: IDSpcz[] = dSpczsToCheck.filter(isPresent);
    if (dSpczs.length > 0) {
      const dSpczCollectionIdentifiers = dSpczCollection.map(dSpczItem => getDSpczIdentifier(dSpczItem)!);
      const dSpczsToAdd = dSpczs.filter(dSpczItem => {
        const dSpczIdentifier = getDSpczIdentifier(dSpczItem);
        if (dSpczIdentifier == null || dSpczCollectionIdentifiers.includes(dSpczIdentifier)) {
          return false;
        }
        dSpczCollectionIdentifiers.push(dSpczIdentifier);
        return true;
      });
      return [...dSpczsToAdd, ...dSpczCollection];
    }
    return dSpczCollection;
  }

  protected convertDateFromClient(dSpcz: IDSpcz): IDSpcz {
    return Object.assign({}, dSpcz, {
      rq: dSpcz.rq?.isValid() ? dSpcz.rq.toJSON() : undefined,
      czrq: dSpcz.czrq?.isValid() ? dSpcz.czrq.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.rq = res.body.rq ? dayjs(res.body.rq) : undefined;
      res.body.czrq = res.body.czrq ? dayjs(res.body.czrq) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dSpcz: IDSpcz) => {
        dSpcz.rq = dSpcz.rq ? dayjs(dSpcz.rq) : undefined;
        dSpcz.czrq = dSpcz.czrq ? dayjs(dSpcz.czrq) : undefined;
      });
    }
    return res;
  }
}
