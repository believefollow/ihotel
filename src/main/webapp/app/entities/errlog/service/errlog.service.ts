import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IErrlog, getErrlogIdentifier } from '../errlog.model';

export type EntityResponseType = HttpResponse<IErrlog>;
export type EntityArrayResponseType = HttpResponse<IErrlog[]>;

@Injectable({ providedIn: 'root' })
export class ErrlogService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/errlogs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/errlogs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(errlog: IErrlog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(errlog);
    return this.http
      .post<IErrlog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(errlog: IErrlog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(errlog);
    return this.http
      .put<IErrlog>(`${this.resourceUrl}/${getErrlogIdentifier(errlog) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(errlog: IErrlog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(errlog);
    return this.http
      .patch<IErrlog>(`${this.resourceUrl}/${getErrlogIdentifier(errlog) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IErrlog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IErrlog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IErrlog[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addErrlogToCollectionIfMissing(errlogCollection: IErrlog[], ...errlogsToCheck: (IErrlog | null | undefined)[]): IErrlog[] {
    const errlogs: IErrlog[] = errlogsToCheck.filter(isPresent);
    if (errlogs.length > 0) {
      const errlogCollectionIdentifiers = errlogCollection.map(errlogItem => getErrlogIdentifier(errlogItem)!);
      const errlogsToAdd = errlogs.filter(errlogItem => {
        const errlogIdentifier = getErrlogIdentifier(errlogItem);
        if (errlogIdentifier == null || errlogCollectionIdentifiers.includes(errlogIdentifier)) {
          return false;
        }
        errlogCollectionIdentifiers.push(errlogIdentifier);
        return true;
      });
      return [...errlogsToAdd, ...errlogCollection];
    }
    return errlogCollection;
  }

  protected convertDateFromClient(errlog: IErrlog): IErrlog {
    return Object.assign({}, errlog, {
      errtime: errlog.errtime?.isValid() ? errlog.errtime.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.errtime = res.body.errtime ? dayjs(res.body.errtime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((errlog: IErrlog) => {
        errlog.errtime = errlog.errtime ? dayjs(errlog.errtime) : undefined;
      });
    }
    return res;
  }
}
