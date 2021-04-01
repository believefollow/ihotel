import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IFwYlwp, getFwYlwpIdentifier } from '../fw-ylwp.model';

export type EntityResponseType = HttpResponse<IFwYlwp>;
export type EntityArrayResponseType = HttpResponse<IFwYlwp[]>;

@Injectable({ providedIn: 'root' })
export class FwYlwpService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/fw-ylwps');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/fw-ylwps');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(fwYlwp: IFwYlwp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fwYlwp);
    return this.http
      .post<IFwYlwp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fwYlwp: IFwYlwp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fwYlwp);
    return this.http
      .put<IFwYlwp>(`${this.resourceUrl}/${getFwYlwpIdentifier(fwYlwp) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(fwYlwp: IFwYlwp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fwYlwp);
    return this.http
      .patch<IFwYlwp>(`${this.resourceUrl}/${getFwYlwpIdentifier(fwYlwp) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFwYlwp>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFwYlwp[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFwYlwp[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addFwYlwpToCollectionIfMissing(fwYlwpCollection: IFwYlwp[], ...fwYlwpsToCheck: (IFwYlwp | null | undefined)[]): IFwYlwp[] {
    const fwYlwps: IFwYlwp[] = fwYlwpsToCheck.filter(isPresent);
    if (fwYlwps.length > 0) {
      const fwYlwpCollectionIdentifiers = fwYlwpCollection.map(fwYlwpItem => getFwYlwpIdentifier(fwYlwpItem)!);
      const fwYlwpsToAdd = fwYlwps.filter(fwYlwpItem => {
        const fwYlwpIdentifier = getFwYlwpIdentifier(fwYlwpItem);
        if (fwYlwpIdentifier == null || fwYlwpCollectionIdentifiers.includes(fwYlwpIdentifier)) {
          return false;
        }
        fwYlwpCollectionIdentifiers.push(fwYlwpIdentifier);
        return true;
      });
      return [...fwYlwpsToAdd, ...fwYlwpCollection];
    }
    return fwYlwpCollection;
  }

  protected convertDateFromClient(fwYlwp: IFwYlwp): IFwYlwp {
    return Object.assign({}, fwYlwp, {
      sdrq: fwYlwp.sdrq?.isValid() ? fwYlwp.sdrq.toJSON() : undefined,
      rlrq: fwYlwp.rlrq?.isValid() ? fwYlwp.rlrq.toJSON() : undefined,
      czrq: fwYlwp.czrq?.isValid() ? fwYlwp.czrq.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.sdrq = res.body.sdrq ? dayjs(res.body.sdrq) : undefined;
      res.body.rlrq = res.body.rlrq ? dayjs(res.body.rlrq) : undefined;
      res.body.czrq = res.body.czrq ? dayjs(res.body.czrq) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fwYlwp: IFwYlwp) => {
        fwYlwp.sdrq = fwYlwp.sdrq ? dayjs(fwYlwp.sdrq) : undefined;
        fwYlwp.rlrq = fwYlwp.rlrq ? dayjs(fwYlwp.rlrq) : undefined;
        fwYlwp.czrq = fwYlwp.czrq ? dayjs(fwYlwp.czrq) : undefined;
      });
    }
    return res;
  }
}
