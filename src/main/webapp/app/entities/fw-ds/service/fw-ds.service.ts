import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IFwDs, getFwDsIdentifier } from '../fw-ds.model';

export type EntityResponseType = HttpResponse<IFwDs>;
export type EntityArrayResponseType = HttpResponse<IFwDs[]>;

@Injectable({ providedIn: 'root' })
export class FwDsService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/fw-ds');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/fw-ds');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(fwDs: IFwDs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fwDs);
    return this.http
      .post<IFwDs>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fwDs: IFwDs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fwDs);
    return this.http
      .put<IFwDs>(`${this.resourceUrl}/${getFwDsIdentifier(fwDs) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(fwDs: IFwDs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fwDs);
    return this.http
      .patch<IFwDs>(`${this.resourceUrl}/${getFwDsIdentifier(fwDs) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFwDs>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFwDs[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFwDs[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addFwDsToCollectionIfMissing(fwDsCollection: IFwDs[], ...fwDsToCheck: (IFwDs | null | undefined)[]): IFwDs[] {
    const fwDs: IFwDs[] = fwDsToCheck.filter(isPresent);
    if (fwDs.length > 0) {
      const fwDsCollectionIdentifiers = fwDsCollection.map(fwDsItem => getFwDsIdentifier(fwDsItem)!);
      const fwDsToAdd = fwDs.filter(fwDsItem => {
        const fwDsIdentifier = getFwDsIdentifier(fwDsItem);
        if (fwDsIdentifier == null || fwDsCollectionIdentifiers.includes(fwDsIdentifier)) {
          return false;
        }
        fwDsCollectionIdentifiers.push(fwDsIdentifier);
        return true;
      });
      return [...fwDsToAdd, ...fwDsCollection];
    }
    return fwDsCollection;
  }

  protected convertDateFromClient(fwDs: IFwDs): IFwDs {
    return Object.assign({}, fwDs, {
      hoteltime: fwDs.hoteltime?.isValid() ? fwDs.hoteltime.toJSON() : undefined,
      rq: fwDs.rq?.isValid() ? fwDs.rq.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.hoteltime = res.body.hoteltime ? dayjs(res.body.hoteltime) : undefined;
      res.body.rq = res.body.rq ? dayjs(res.body.rq) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fwDs: IFwDs) => {
        fwDs.hoteltime = fwDs.hoteltime ? dayjs(fwDs.hoteltime) : undefined;
        fwDs.rq = fwDs.rq ? dayjs(fwDs.rq) : undefined;
      });
    }
    return res;
  }
}
