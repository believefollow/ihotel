import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IFwJywp, getFwJywpIdentifier } from '../fw-jywp.model';

export type EntityResponseType = HttpResponse<IFwJywp>;
export type EntityArrayResponseType = HttpResponse<IFwJywp[]>;

@Injectable({ providedIn: 'root' })
export class FwJywpService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/fw-jywps');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/fw-jywps');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(fwJywp: IFwJywp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fwJywp);
    return this.http
      .post<IFwJywp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fwJywp: IFwJywp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fwJywp);
    return this.http
      .put<IFwJywp>(`${this.resourceUrl}/${getFwJywpIdentifier(fwJywp) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(fwJywp: IFwJywp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fwJywp);
    return this.http
      .patch<IFwJywp>(`${this.resourceUrl}/${getFwJywpIdentifier(fwJywp) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFwJywp>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFwJywp[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFwJywp[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addFwJywpToCollectionIfMissing(fwJywpCollection: IFwJywp[], ...fwJywpsToCheck: (IFwJywp | null | undefined)[]): IFwJywp[] {
    const fwJywps: IFwJywp[] = fwJywpsToCheck.filter(isPresent);
    if (fwJywps.length > 0) {
      const fwJywpCollectionIdentifiers = fwJywpCollection.map(fwJywpItem => getFwJywpIdentifier(fwJywpItem)!);
      const fwJywpsToAdd = fwJywps.filter(fwJywpItem => {
        const fwJywpIdentifier = getFwJywpIdentifier(fwJywpItem);
        if (fwJywpIdentifier == null || fwJywpCollectionIdentifiers.includes(fwJywpIdentifier)) {
          return false;
        }
        fwJywpCollectionIdentifiers.push(fwJywpIdentifier);
        return true;
      });
      return [...fwJywpsToAdd, ...fwJywpCollection];
    }
    return fwJywpCollection;
  }

  protected convertDateFromClient(fwJywp: IFwJywp): IFwJywp {
    return Object.assign({}, fwJywp, {
      jyrq: fwJywp.jyrq?.isValid() ? fwJywp.jyrq.toJSON() : undefined,
      ghrq: fwJywp.ghrq?.isValid() ? fwJywp.ghrq.toJSON() : undefined,
      djrq: fwJywp.djrq?.isValid() ? fwJywp.djrq.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.jyrq = res.body.jyrq ? dayjs(res.body.jyrq) : undefined;
      res.body.ghrq = res.body.ghrq ? dayjs(res.body.ghrq) : undefined;
      res.body.djrq = res.body.djrq ? dayjs(res.body.djrq) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fwJywp: IFwJywp) => {
        fwJywp.jyrq = fwJywp.jyrq ? dayjs(fwJywp.jyrq) : undefined;
        fwJywp.ghrq = fwJywp.ghrq ? dayjs(fwJywp.ghrq) : undefined;
        fwJywp.djrq = fwJywp.djrq ? dayjs(fwJywp.djrq) : undefined;
      });
    }
    return res;
  }
}
