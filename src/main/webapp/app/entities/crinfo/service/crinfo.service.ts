import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICrinfo, getCrinfoIdentifier } from '../crinfo.model';

export type EntityResponseType = HttpResponse<ICrinfo>;
export type EntityArrayResponseType = HttpResponse<ICrinfo[]>;

@Injectable({ providedIn: 'root' })
export class CrinfoService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/crinfos');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/crinfos');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(crinfo: ICrinfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(crinfo);
    return this.http
      .post<ICrinfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(crinfo: ICrinfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(crinfo);
    return this.http
      .put<ICrinfo>(`${this.resourceUrl}/${getCrinfoIdentifier(crinfo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(crinfo: ICrinfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(crinfo);
    return this.http
      .patch<ICrinfo>(`${this.resourceUrl}/${getCrinfoIdentifier(crinfo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICrinfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICrinfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICrinfo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addCrinfoToCollectionIfMissing(crinfoCollection: ICrinfo[], ...crinfosToCheck: (ICrinfo | null | undefined)[]): ICrinfo[] {
    const crinfos: ICrinfo[] = crinfosToCheck.filter(isPresent);
    if (crinfos.length > 0) {
      const crinfoCollectionIdentifiers = crinfoCollection.map(crinfoItem => getCrinfoIdentifier(crinfoItem)!);
      const crinfosToAdd = crinfos.filter(crinfoItem => {
        const crinfoIdentifier = getCrinfoIdentifier(crinfoItem);
        if (crinfoIdentifier == null || crinfoCollectionIdentifiers.includes(crinfoIdentifier)) {
          return false;
        }
        crinfoCollectionIdentifiers.push(crinfoIdentifier);
        return true;
      });
      return [...crinfosToAdd, ...crinfoCollection];
    }
    return crinfoCollection;
  }

  protected convertDateFromClient(crinfo: ICrinfo): ICrinfo {
    return Object.assign({}, crinfo, {
      operatetime: crinfo.operatetime?.isValid() ? crinfo.operatetime.toJSON() : undefined,
      hoteltime: crinfo.hoteltime?.isValid() ? crinfo.hoteltime.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.operatetime = res.body.operatetime ? dayjs(res.body.operatetime) : undefined;
      res.body.hoteltime = res.body.hoteltime ? dayjs(res.body.hoteltime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((crinfo: ICrinfo) => {
        crinfo.operatetime = crinfo.operatetime ? dayjs(crinfo.operatetime) : undefined;
        crinfo.hoteltime = crinfo.hoteltime ? dayjs(crinfo.hoteltime) : undefined;
      });
    }
    return res;
  }
}
