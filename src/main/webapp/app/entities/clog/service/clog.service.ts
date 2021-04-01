import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IClog, getClogIdentifier } from '../clog.model';

export type EntityResponseType = HttpResponse<IClog>;
export type EntityArrayResponseType = HttpResponse<IClog[]>;

@Injectable({ providedIn: 'root' })
export class ClogService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/clogs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/clogs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(clog: IClog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(clog);
    return this.http
      .post<IClog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(clog: IClog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(clog);
    return this.http
      .put<IClog>(`${this.resourceUrl}/${getClogIdentifier(clog) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(clog: IClog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(clog);
    return this.http
      .patch<IClog>(`${this.resourceUrl}/${getClogIdentifier(clog) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IClog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClog[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addClogToCollectionIfMissing(clogCollection: IClog[], ...clogsToCheck: (IClog | null | undefined)[]): IClog[] {
    const clogs: IClog[] = clogsToCheck.filter(isPresent);
    if (clogs.length > 0) {
      const clogCollectionIdentifiers = clogCollection.map(clogItem => getClogIdentifier(clogItem)!);
      const clogsToAdd = clogs.filter(clogItem => {
        const clogIdentifier = getClogIdentifier(clogItem);
        if (clogIdentifier == null || clogCollectionIdentifiers.includes(clogIdentifier)) {
          return false;
        }
        clogCollectionIdentifiers.push(clogIdentifier);
        return true;
      });
      return [...clogsToAdd, ...clogCollection];
    }
    return clogCollection;
  }

  protected convertDateFromClient(clog: IClog): IClog {
    return Object.assign({}, clog, {
      begindate: clog.begindate?.isValid() ? clog.begindate.toJSON() : undefined,
      enddate: clog.enddate?.isValid() ? clog.enddate.toJSON() : undefined,
      dqrq: clog.dqrq?.isValid() ? clog.dqrq.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.begindate = res.body.begindate ? dayjs(res.body.begindate) : undefined;
      res.body.enddate = res.body.enddate ? dayjs(res.body.enddate) : undefined;
      res.body.dqrq = res.body.dqrq ? dayjs(res.body.dqrq) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((clog: IClog) => {
        clog.begindate = clog.begindate ? dayjs(clog.begindate) : undefined;
        clog.enddate = clog.enddate ? dayjs(clog.enddate) : undefined;
        clog.dqrq = clog.dqrq ? dayjs(clog.dqrq) : undefined;
      });
    }
    return res;
  }
}
