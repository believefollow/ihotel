import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IFwWxf, getFwWxfIdentifier } from '../fw-wxf.model';

export type EntityResponseType = HttpResponse<IFwWxf>;
export type EntityArrayResponseType = HttpResponse<IFwWxf[]>;

@Injectable({ providedIn: 'root' })
export class FwWxfService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/fw-wxfs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/fw-wxfs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(fwWxf: IFwWxf): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fwWxf);
    return this.http
      .post<IFwWxf>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fwWxf: IFwWxf): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fwWxf);
    return this.http
      .put<IFwWxf>(`${this.resourceUrl}/${getFwWxfIdentifier(fwWxf) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(fwWxf: IFwWxf): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fwWxf);
    return this.http
      .patch<IFwWxf>(`${this.resourceUrl}/${getFwWxfIdentifier(fwWxf) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFwWxf>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFwWxf[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFwWxf[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addFwWxfToCollectionIfMissing(fwWxfCollection: IFwWxf[], ...fwWxfsToCheck: (IFwWxf | null | undefined)[]): IFwWxf[] {
    const fwWxfs: IFwWxf[] = fwWxfsToCheck.filter(isPresent);
    if (fwWxfs.length > 0) {
      const fwWxfCollectionIdentifiers = fwWxfCollection.map(fwWxfItem => getFwWxfIdentifier(fwWxfItem)!);
      const fwWxfsToAdd = fwWxfs.filter(fwWxfItem => {
        const fwWxfIdentifier = getFwWxfIdentifier(fwWxfItem);
        if (fwWxfIdentifier == null || fwWxfCollectionIdentifiers.includes(fwWxfIdentifier)) {
          return false;
        }
        fwWxfCollectionIdentifiers.push(fwWxfIdentifier);
        return true;
      });
      return [...fwWxfsToAdd, ...fwWxfCollection];
    }
    return fwWxfCollection;
  }

  protected convertDateFromClient(fwWxf: IFwWxf): IFwWxf {
    return Object.assign({}, fwWxf, {
      djrq: fwWxf.djrq?.isValid() ? fwWxf.djrq.toJSON() : undefined,
      wcrq: fwWxf.wcrq?.isValid() ? fwWxf.wcrq.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.djrq = res.body.djrq ? dayjs(res.body.djrq) : undefined;
      res.body.wcrq = res.body.wcrq ? dayjs(res.body.wcrq) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fwWxf: IFwWxf) => {
        fwWxf.djrq = fwWxf.djrq ? dayjs(fwWxf.djrq) : undefined;
        fwWxf.wcrq = fwWxf.wcrq ? dayjs(fwWxf.wcrq) : undefined;
      });
    }
    return res;
  }
}
