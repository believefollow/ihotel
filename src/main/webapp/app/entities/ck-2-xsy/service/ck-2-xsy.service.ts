import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICk2xsy, getCk2xsyIdentifier } from '../ck-2-xsy.model';

export type EntityResponseType = HttpResponse<ICk2xsy>;
export type EntityArrayResponseType = HttpResponse<ICk2xsy[]>;

@Injectable({ providedIn: 'root' })
export class Ck2xsyService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/ck-2-xsies');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/ck-2-xsies');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(ck2xsy: ICk2xsy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ck2xsy);
    return this.http
      .post<ICk2xsy>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ck2xsy: ICk2xsy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ck2xsy);
    return this.http
      .put<ICk2xsy>(`${this.resourceUrl}/${getCk2xsyIdentifier(ck2xsy) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(ck2xsy: ICk2xsy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ck2xsy);
    return this.http
      .patch<ICk2xsy>(`${this.resourceUrl}/${getCk2xsyIdentifier(ck2xsy) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICk2xsy>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICk2xsy[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICk2xsy[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addCk2xsyToCollectionIfMissing(ck2xsyCollection: ICk2xsy[], ...ck2xsiesToCheck: (ICk2xsy | null | undefined)[]): ICk2xsy[] {
    const ck2xsies: ICk2xsy[] = ck2xsiesToCheck.filter(isPresent);
    if (ck2xsies.length > 0) {
      const ck2xsyCollectionIdentifiers = ck2xsyCollection.map(ck2xsyItem => getCk2xsyIdentifier(ck2xsyItem)!);
      const ck2xsiesToAdd = ck2xsies.filter(ck2xsyItem => {
        const ck2xsyIdentifier = getCk2xsyIdentifier(ck2xsyItem);
        if (ck2xsyIdentifier == null || ck2xsyCollectionIdentifiers.includes(ck2xsyIdentifier)) {
          return false;
        }
        ck2xsyCollectionIdentifiers.push(ck2xsyIdentifier);
        return true;
      });
      return [...ck2xsiesToAdd, ...ck2xsyCollection];
    }
    return ck2xsyCollection;
  }

  protected convertDateFromClient(ck2xsy: ICk2xsy): ICk2xsy {
    return Object.assign({}, ck2xsy, {
      rq: ck2xsy.rq?.isValid() ? ck2xsy.rq.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.rq = res.body.rq ? dayjs(res.body.rq) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ck2xsy: ICk2xsy) => {
        ck2xsy.rq = ck2xsy.rq ? dayjs(ck2xsy.rq) : undefined;
      });
    }
    return res;
  }
}
